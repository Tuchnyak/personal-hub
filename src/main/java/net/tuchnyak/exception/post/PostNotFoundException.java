package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostNotFoundException extends PersonalHubException {

    private static final String MESSAGE = "Post with id %s not found!";

    public PostNotFoundException(UUID postId) {
        super(MESSAGE.formatted(postId.toString()));
    }

    public PostNotFoundException(String message) {
        super(message);
    }

}
