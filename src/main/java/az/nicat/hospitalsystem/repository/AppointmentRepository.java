package az.nicat.hospitalsystem.repository;

import az.nicat.hospitalsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
