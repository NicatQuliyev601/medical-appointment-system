package az.nicat.hospitalsystem.controller;

import az.nicat.hospitalsystem.dto.request.AppointmentRequest;
import az.nicat.hospitalsystem.dto.request.SignUpPatientRequest;
import az.nicat.hospitalsystem.dto.response.AppointmentResponse;
import az.nicat.hospitalsystem.dto.response.Response;
import az.nicat.hospitalsystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<AppointmentResponse> scheduleAppointment(@RequestBody AppointmentRequest request,
                                                                   @PathVariable Long patientId,
                                                                   @PathVariable Long doctorId) {
        return new ResponseEntity<>(service.scheduleAppointment(request, patientId, doctorId), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);

    }
}
