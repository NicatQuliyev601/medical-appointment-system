package az.nicat.hospitalsystem.service;

import az.nicat.hospitalsystem.dto.request.AppointmentRequest;
import az.nicat.hospitalsystem.dto.response.AppointmentResponse;
import az.nicat.hospitalsystem.dto.response.UserResponse;
import az.nicat.hospitalsystem.entity.Appointment;
import az.nicat.hospitalsystem.entity.User;
import az.nicat.hospitalsystem.entity.enums.AppointmentStatus;
import az.nicat.hospitalsystem.exception.ErrorCodes;
import az.nicat.hospitalsystem.exception.UserNotFoundException;
import az.nicat.hospitalsystem.repository.AppointmentRepository;
import az.nicat.hospitalsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentResponse scheduleAppointment(AppointmentRequest request, Long patientId, Long doctorId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        Appointment appointment = Appointment.builder()
                .dateTime(request.getDateTime())
                .status(AppointmentStatus.BOOKED)
                .patient(patient)
                .doctor(doctor)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        ModelMapper modelMapper = new ModelMapper();

        UserResponse userResponsePatient = modelMapper.map(savedAppointment.getPatient(), UserResponse.class);
        UserResponse userResponseDoctor = modelMapper.map(savedAppointment.getDoctor(), UserResponse.class);

        return AppointmentResponse.builder()
                .id(savedAppointment.getId())
                .dateTime(savedAppointment.getDateTime())
                .status(savedAppointment.getStatus())
                .patient(userResponsePatient)
                .doctor(userResponseDoctor)
                .feedbacks(savedAppointment.getFeedbacks())
                .build();
    }

    public List<AppointmentResponse> findAll() {
        return appointmentRepository
                .findAll()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
    }
}
