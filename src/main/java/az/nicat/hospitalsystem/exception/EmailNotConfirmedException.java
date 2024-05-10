package az.nicat.hospitalsystem.exception;

import lombok.Getter;

@Getter
public class EmailNotConfirmedException extends RuntimeException {

    public final ErrorCodes errorCode;

    public EmailNotConfirmedException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }
}
