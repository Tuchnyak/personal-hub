package net.tuchnyak.exception.post;

import net.tuchnyak.exception.PersonalHubException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostReplaceBySlyException extends PersonalHubException {

    public PostReplaceBySlyException(String slug, Exception cause) {
        super("Error during post replacement with slug %s".formatted(slug), cause);
    }

}
