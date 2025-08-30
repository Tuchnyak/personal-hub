package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostBodyIsEmptyException extends PersonalHubException {

    private static final String MESSAGE = "Post body shouldn't be empty!";

    public PostBodyIsEmptyException() {
        super(MESSAGE);
    }

}
