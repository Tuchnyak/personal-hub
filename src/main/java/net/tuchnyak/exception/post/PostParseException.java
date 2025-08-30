package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostParseException extends PersonalHubException {

    public PostParseException(String message, Throwable e) {
        super(message, e);
    }

}
