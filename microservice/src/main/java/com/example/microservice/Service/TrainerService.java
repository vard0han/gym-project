package com.example.microservice.Service;

import com.example.microservice.DTO.TrainerWorkloadRequest;
import com.example.microservice.Model.Trainer;
import com.example.microservice.Repository.TrainerRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.YearMonth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;

    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    @CircuitBreaker(name = "trainerServiceCircuitBreaker", fallbackMethod = "fallbackUpdateTrainerWorkload")
    public void updateTrainerWorkload(TrainerWorkloadRequest request) {
        Trainer trainer = trainerRepository.findByUsername(request.getUsername())
                .orElse(new Trainer(request.getUsername(), request.getFirstName(), request.getLastName(), request.isActive()));
        YearMonth yearMonth = YearMonth.from(request.getTrainingDate());
        long hours = request.getTrainingDuration().toHours();

        trainer.updateTrainingDuration(yearMonth, hours, request.getActionType());
        trainerRepository.save(trainer);
    }

    public void fallbackUpdateTrainerWorkload(TrainerWorkloadRequest request, Throwable t) {
        logger.error("Error occurred while updating trainer workload: " + t.getMessage());
    }
}
