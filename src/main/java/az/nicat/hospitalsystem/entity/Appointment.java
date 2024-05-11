package az.nicat.hospitalsystem.entity;

import az.nicat.hospitalsystem.entity.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    @ToString.Exclude
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    @ToString.Exclude
    private User doctor;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.REMOVE)
    private List<Feedback> feedbacks = new ArrayList<>();
}
