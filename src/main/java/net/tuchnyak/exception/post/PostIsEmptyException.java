package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostIsEmptyException extends PersonalHubException {

    private static final String MESSAGE = "Post content shouldn't be empty!";

    public PostIsEmptyException() {
        super(MESSAGE);
    }

}
