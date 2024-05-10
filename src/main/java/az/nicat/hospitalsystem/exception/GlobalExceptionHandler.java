package az.nicat.hospitalsystem.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailExistException(EmailExistException ex,
                                                                      WebRequest req) {

        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Email already exist")
                .build());
    }

    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameExistException(UserNameExistException ex,
                                                                         WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Username already exist")
                .build());
    }

    @ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorityNotFoundException(AuthorityNotFoundException ex,
                                                                             WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Authority not found")
                .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex,
                                                                        WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("User not found")
                .build());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(InvalidPasswordException ex,
                                                                           WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Invalid password")
                .build());
    }

    @ExceptionHandler(EmailNotConfirmedException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailNotConfirmedException(EmailNotConfirmedException ex,
                                                                             WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Email not confirmed")
                .build());
    }

}
