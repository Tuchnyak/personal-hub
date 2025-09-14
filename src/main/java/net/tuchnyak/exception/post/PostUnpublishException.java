package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostUnpublishException extends PersonalHubException {

    public static final String MESSAGE = "Post with id %s cannot be unpublished";

    public PostUnpublishException() {
        super(MESSAGE.formatted("unknown"));
    }

    public PostUnpublishException(UUID postId, Throwable cause) {
        super(MESSAGE.formatted(postId.toString()), cause);
    }

}
