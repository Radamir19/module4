package com.example.module4.controller;


import com.example.module4.exception.NotFoundException;
import com.example.module4.model.Teacher;
import com.example.module4.model.dto.TeacherDto;
import com.example.module4.repository.TeacherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TeacherControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("lms")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void shouldCreateNewTeacher() {
        TeacherDto request = new TeacherDto(null, "Иван", "Петров");

        ResponseEntity<TeacherDto> response =
                template.postForEntity("/api/v1/teachers", request, TeacherDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        TeacherDto body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertNotNull(body.id());
        Assertions.assertEquals("Иван", body.name());
        Assertions.assertEquals("Петров", body.surname());

        Teacher saved = teacherRepository.findById(body.id()).orElseThrow(() -> new NotFoundException(""));
        Assertions.assertEquals("Иван", saved.getName());
        Assertions.assertEquals("Петров", saved.getSurname());
    }

    @Test
    void createTeacher_returns400WhenNameIsBlank() {
        TeacherDto request = new TeacherDto(null, "", "Петров");

        ResponseEntity<String> response =
                template.postForEntity("/api/v1/teachers", request, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(0, teacherRepository.count());
    }

    @Test
    void createTeacher_returns400WhenSurnameIsBlank() {
        TeacherDto request = new TeacherDto(null, "Petr", "");

        ResponseEntity<String> response =
                template.postForEntity("/api/v1/teachers", request, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldGetTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Ivan");
        teacher.setSurname("Petrov");
        Teacher saved = teacherRepository.save(teacher);

        ResponseEntity<TeacherDto> response =
                template.getForEntity("/api/v1/teachers/" + saved.getId(), TeacherDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        TeacherDto body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(saved.getId(), body.id());
        Assertions.assertEquals("Ivan", body.name());
        Assertions.assertEquals("Petrov", body.surname());
    }

    @Test
    void getTeacher_returns404WhenIdIsNotFound() {
        ResponseEntity<String> response =
                template.getForEntity("/api/v1/teachers/99999", String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Ivan");
        teacher.setSurname("Petrov");
        Teacher saved = teacherRepository.save(teacher);

        TeacherDto requestUpdate = new TeacherDto(saved.getId(), "Petr", "Ivanov");
        ResponseEntity<TeacherDto> response = template.exchange(
                "/api/v1/teachers/" + saved.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(requestUpdate),
                TeacherDto.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        TeacherDto body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals("Petr", body.name());
        Assertions.assertEquals("Ivanov", body.surname());

        Teacher updatedDb = teacherRepository.findById(saved.getId()).orElseThrow();
        Assertions.assertEquals("Petr", updatedDb.getName());
        Assertions.assertEquals("Ivanov", updatedDb.getSurname());
    }

    @Test
    void shouldDeleteTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Ivan");
        teacher.setSurname("Petrov");
        Teacher saved = teacherRepository.save(teacher);

        ResponseEntity<Void> response = template.exchange(
                "/api/v1/teachers/" + saved.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        boolean exists = teacherRepository.existsById(saved.getId());
        Assertions.assertFalse(exists);
    }

    @Test
    void shouldGetAllTeachersWithPagination() throws JsonProcessingException {
        Teacher teacher1 = new Teacher();
        teacher1.setName("Ivan");
        teacher1.setSurname("Petrov");
        Teacher saved1 = teacherRepository.save(teacher1);
        Teacher teacher2 = new Teacher();
        teacher2.setName("Petr");
        teacher2.setSurname("Ivanov");
        Teacher saved2 = teacherRepository.save(teacher2);

        ResponseEntity<String> response = template.exchange(
                "/api/v1/teachers?page=0&size=2",
                HttpMethod.GET,
                null,
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        JsonNode json = objectMapper.readTree(response.getBody());

        Assertions.assertTrue(json.get("content").isArray());
        Assertions.assertEquals(2, json.get("content").size());
        Assertions.assertEquals(2, json.get("totalElements").asLong());
        Assertions.assertEquals(1, json.get("totalPages").asInt());
        Assertions.assertEquals(0, json.get("number").asInt());
        Assertions.assertEquals(2, json.get("size").asInt());
    }


}
