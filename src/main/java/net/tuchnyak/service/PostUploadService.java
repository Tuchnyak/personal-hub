package net.tuchnyak.service;

import net.tuchnyak.dto.Page;
import net.tuchnyak.dto.PostListItem;
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

    Page<PostListItem> getPublishedPostListPaginated(int page, int pageSize);

}
