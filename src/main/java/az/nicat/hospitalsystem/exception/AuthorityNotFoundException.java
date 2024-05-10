package az.nicat.hospitalsystem.exception;

import lombok.Getter;

@Getter
public class AuthorityNotFoundException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;

    public AuthorityNotFoundException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
