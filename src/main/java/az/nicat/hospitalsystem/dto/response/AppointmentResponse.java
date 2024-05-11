package az.nicat.hospitalsystem.dto.response;

import az.nicat.hospitalsystem.entity.Feedback;
import az.nicat.hospitalsystem.entity.User;
import az.nicat.hospitalsystem.entity.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;

    private LocalDateTime dateTime;
    private AppointmentStatus status;

    private UserResponse patient;
    private UserResponse doctor;

    private List<Feedback> feedbacks = new ArrayList<>();
}
