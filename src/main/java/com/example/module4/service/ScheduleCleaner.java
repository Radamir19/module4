package com.example.module4.service;

import com.example.module4.repository.ScheduleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class ScheduleCleaner {
    private final ScheduleRepository scheduleRepository;

    public ScheduleCleaner(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Scheduled(cron = "${spring.app.cleanup.cron}")
    @Transactional
    public void deleteOldSchedule() {
        scheduleRepository.deleteSchedules(OffsetDateTime.now().minusYears(1));
    }
}
