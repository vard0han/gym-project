package com.example.microservice.DTO;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainerWorkloadRequest {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate trainingDate;
    private Duration trainingDuration;
    private String actionType;
}
