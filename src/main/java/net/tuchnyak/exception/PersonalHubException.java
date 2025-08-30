package net.tuchnyak.exception;

/**
 * @author tuchnyak (George Shchennikov)
 */
public abstract class PersonalHubException extends RuntimeException {

    public PersonalHubException(String message) {
        super(message);
    }

    public PersonalHubException(String message, Throwable cause) {
        super(message, cause);
    }

}
