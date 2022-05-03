package br.com.novoanjo.service.schedule.impl;

import br.com.novoanjo.service.event.EventService;
import br.com.novoanjo.service.schedule.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@EnableScheduling
@Configuration
@Slf4j
public class SchedulerEventoPendenteServiceImpl implements SchedulerService {

    @Autowired
    private EventService eventService;

    @Scheduled(cron = "${service.scheduler.cron}", zone = "${service.scheduler.time-zone}")
    public void process(){
        log.info("SchedulerServiceImpl.process() - start , now {}", LocalDateTime.now());
        eventService.verificaEventosPendente();
        log.info("SchedulerServiceImpl.process() - end , now {}", LocalDateTime.now());
    }
}
