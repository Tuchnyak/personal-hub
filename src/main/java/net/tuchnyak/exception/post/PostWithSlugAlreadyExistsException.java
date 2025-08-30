package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostWithSlugAlreadyExistsException extends PersonalHubException {

    public static final String MESSAGE_TEMPLATE = "Post with slug '%s' already exists!";

    public PostWithSlugAlreadyExistsException(String slug) {
        super(String.format(MESSAGE_TEMPLATE, slug));
    }

}
