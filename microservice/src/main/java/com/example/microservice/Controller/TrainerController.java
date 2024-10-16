package com.example.microservice.Controller;

import com.example.microservice.DTO.TrainerWorkloadRequest;
import com.example.microservice.Service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class TrainerController {

    @Autowired
    TrainerService trainerService;

    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @JmsListener(destination = "trainerWorkloadQueue")
    public void updateTrainerWorkload(@RequestBody TrainerWorkloadRequest request) {
        logger.info("Received request to update workload for trainer: {}", request.getUsername());

        try {
            trainerService.updateTrainerWorkload(request);
            logger.info("Workload updated successfully for trainer: {}", request.getUsername());
        } catch (Exception e) {
            logger.error("Error occurred while updating trainer workload: " + e.getMessage());
        }
    }
}
