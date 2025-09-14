package net.tuchnyak.exception.auth;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class NotAuthenticatedActionException extends PersonalHubException {

    public NotAuthenticatedActionException(String message) {
        super(message);
    }

}
