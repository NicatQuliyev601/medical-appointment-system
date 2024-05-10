package az.nicat.hospitalsystem.service;

import az.nicat.hospitalsystem.dto.request.ChangePasswordRequest;
import az.nicat.hospitalsystem.dto.request.LoginRequest;
import az.nicat.hospitalsystem.dto.request.SignUpDoctorRequest;
import az.nicat.hospitalsystem.dto.request.SignUpPatientRequest;
import az.nicat.hospitalsystem.dto.response.Response;
import az.nicat.hospitalsystem.dto.response.UserResponse;
import az.nicat.hospitalsystem.entity.Authority;
import az.nicat.hospitalsystem.entity.User;
import az.nicat.hospitalsystem.entity.enums.UserAuthority;
import az.nicat.hospitalsystem.exception.*;
import az.nicat.hospitalsystem.repository.AuthorityRepository;
import az.nicat.hospitalsystem.repository.UserRepository;
import az.nicat.hospitalsystem.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final EmailService emailService;

    public ResponseEntity<Response> registerPatient(SignUpPatientRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailExistException(ErrorCodes.EMAIL_ALREADY_EXIST);
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserNameExistException(ErrorCodes.USERNAME_ALREADY_EXIST);
        }

        Authority userAuthority = authorityRepository.findByAuthority(UserAuthority.PATIENT)
                .orElseGet(() -> {
                    Authority authority = new Authority();
                    authority.setAuthority(UserAuthority.PATIENT);
                    return authorityRepository.save(authority);
                });

        User user = User.builder()
                .authorities(List.of(userAuthority))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(signUpRequest.getPassword())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .contactNumber(signUpRequest.getContactNumber())
                .medicalHistory(signUpRequest.getMedicalHistory())
                .registrationDate(LocalDateTime.now())
                .build();

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        String confirmationToken = getConfirmationToken();

        user.setConfirmationToken(confirmationToken);
        user.setEmailConfirmed(false);
        userRepository.save(user);


        emailService.sendMail(signUpRequest.getEmail(),
                "Confirmation",
                "http://localhost:8080/api/auth/confirmation?confirmationToken=" + confirmationToken);

        return ResponseEntity.ok(Response
                .builder()
                .jwt(jwtService.issueToken(user))
                .build());

    }

    public ResponseEntity<Response> registerDoctor(SignUpDoctorRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailExistException(ErrorCodes.EMAIL_ALREADY_EXIST);
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserNameExistException(ErrorCodes.USERNAME_ALREADY_EXIST);
        }

        Authority userAuthority = authorityRepository.findByAuthority(UserAuthority.DOCTOR)
                .orElseGet(() -> {
                    Authority authority = new Authority();
                    authority.setAuthority(UserAuthority.DOCTOR);
                    return authorityRepository.save(authority);
                });


        User user = User.builder()
                .authorities(List.of(userAuthority))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(signUpRequest.getPassword())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .contactNumber(signUpRequest.getContactNumber())
                .clinicLocation(signUpRequest.getClinicLocation())
                .qualifications(signUpRequest.getQualifications())
                .specialties(signUpRequest.getSpecialties())
                .registrationDate(LocalDateTime.now())
                .build();

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        String confirmationToken = getConfirmationToken();

        user.setConfirmationToken(confirmationToken);
        user.setEmailConfirmed(false);
        userRepository.save(user);


        emailService.sendMail(signUpRequest.getEmail(),
                "Confirmation",
                "http://localhost:8080/api/auth/confirmation?confirmationToken=" + confirmationToken);

        return ResponseEntity.ok(Response
                .builder()
                .jwt(jwtService.issueToken(user))
                .build());

    }

    @Transactional
    public ResponseEntity<Response> loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        if (!user.isEmailConfirmed()) {
            throw new EmailNotConfirmedException(ErrorCodes.EMAIL_NOT_CONFIRMED);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException(ErrorCodes.INVALID_PASSWORD);
        }

        return ResponseEntity.ok(Response.builder()
                .jwt(jwtService.issueToken(user))
                .id(user.getId())
                .authorities(user.getAuthorities())
                .build());
    }


    public ResponseEntity<UserResponse> changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException(ErrorCodes.INVALID_PASSWORD);
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(encodedNewPassword);
        user.setPasswordChanged(true);
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setAuthorities(user.getAuthorities());
        userResponse.setPasswordChanged(user.getPasswordChanged());

        return ResponseEntity.ok(userResponse);
    }


    public ResponseEntity<?> confirmation(String confirmationToken) {
        Optional<User> userOptional = userRepository.findByConfirmationToken(confirmationToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmailConfirmed(true);
            userRepository.save(user);

            return ResponseEntity.ok("User confirmed successfully");
        } else {
            return ResponseEntity.ok("Confirmation token is invalid");
        }
    }

    private String getConfirmationToken() {
        UUID gfg = UUID.randomUUID();
        return gfg.toString();
    }


}
