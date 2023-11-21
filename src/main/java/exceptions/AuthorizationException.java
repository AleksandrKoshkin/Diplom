package exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("The user is not logged in");
    }
}
