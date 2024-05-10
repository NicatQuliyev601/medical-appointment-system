package az.nicat.hospitalsystem.controller;

import az.nicat.hospitalsystem.dto.request.ChangePasswordRequest;
import az.nicat.hospitalsystem.dto.request.LoginRequest;
import az.nicat.hospitalsystem.dto.request.SignUpDoctorRequest;
import az.nicat.hospitalsystem.dto.request.SignUpPatientRequest;
import az.nicat.hospitalsystem.dto.response.Response;
import az.nicat.hospitalsystem.dto.response.UserResponse;
import az.nicat.hospitalsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signupPatient")
    public ResponseEntity<Response> registerPatient(@RequestBody SignUpPatientRequest signUpRequest) {
        return authService.registerPatient(signUpRequest);

    }

    @PostMapping("/signupDoctor")
    public ResponseEntity<Response> registerDoctor(@RequestBody SignUpDoctorRequest signUpRequest) {
        return authService.registerDoctor(signUpRequest);

    }

    @PostMapping("/signin")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/changePassword/users/{userId}")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long userId,
                                                       @RequestBody ChangePasswordRequest request) {
        return authService.changePassword(userId, request);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("confirmationToken") String confirmationToken) {
        return authService.confirmation(confirmationToken);
    }

}
