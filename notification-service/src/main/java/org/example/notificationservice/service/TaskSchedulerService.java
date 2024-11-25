package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskSchedulerService {

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleFileCleanup() {

    }
}
