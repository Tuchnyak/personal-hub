package net.tuchnyak.exception;

import net.tuchnyak.model.blog.Post;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostUploadException extends PersonalHubException {

    private static final String MESSAGE_TEMPLATE = "Error Post uploading: %s";

    public PostUploadException(Post postToSave, Throwable cause) {
        super(MESSAGE_TEMPLATE.formatted(postToSave.toString()), cause);
    }

}
