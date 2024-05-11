package az.nicat.hospitalsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;
    private String lastName;
    private String username;
    private String contactNumber;
    private String medicalHistory;
    private LocalDateTime registrationDate;
    private String qualifications;
    private String specialties;
    private String clinicLocation;
    private String email;
    private String password;
    private Boolean passwordChanged;
    private String confirmationToken;
    private boolean emailConfirmed;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "user_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<Authority> authorities;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Appointment> appointmentsPatient = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
    private List<Appointment> appointmentsDoctor = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Notification> notifications= new ArrayList<>();
}
