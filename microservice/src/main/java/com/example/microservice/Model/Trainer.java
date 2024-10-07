package com.example.microservice.Model;


import lombok.*;

import java.time.YearMonth;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private boolean isActive;

    @ElementCollection(fetch = FetchType.LAZY)
    private Map<YearMonth, Long> monthlyDurations = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(Trainer.class);

    public void updateTrainingDuration(YearMonth month, Long hours, String actionType) {
        if ("ADD".equals(actionType)) {
            monthlyDurations.merge(month, hours, Long::sum);
            logger.info("Duration added: {}", hours);
        } else if ("DELETE".equals(actionType)) {
            monthlyDurations.merge(month, hours, (currentHours, hoursToDelete) -> {
                long updatedHours = currentHours - hoursToDelete;
                return updatedHours < 0 ? 0 : updatedHours; // Ensure the result is not negative
            });
            logger.info("Duration deleted: {}", hours);
        }else{
            throw new IllegalArgumentException("Invalid action type");
        }
    }

}