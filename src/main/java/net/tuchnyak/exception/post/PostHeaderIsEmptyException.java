package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostHeaderIsEmptyException extends PersonalHubException {

    private static final String MESSAGE = "Post header shouldn't be empty!";

    public PostHeaderIsEmptyException() {
        super(MESSAGE);
    }

}
