package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostPublishException extends PersonalHubException {

    public static final String MESSAGE = "Post with id %s cannot be published";

    public PostPublishException() {
        super(MESSAGE.formatted("unknown"));
    }

    public PostPublishException(UUID postId, Throwable cause) {
        super(MESSAGE.formatted(postId.toString()), cause);
    }

}
