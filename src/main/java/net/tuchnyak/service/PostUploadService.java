package net.tuchnyak.service;

import net.tuchnyak.model.blog.Post;

import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface PostUploadService {

    Post uploadPost(String rawPostContent);

    UUID uploadByReplace(String rawPostContent, String slug);

    void publishPost(UUID postId);

    Post getBySlug(String slug);

}
