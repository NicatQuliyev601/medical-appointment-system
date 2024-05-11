package az.nicat.hospitalsystem.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    private LocalDateTime dateTime;
}
