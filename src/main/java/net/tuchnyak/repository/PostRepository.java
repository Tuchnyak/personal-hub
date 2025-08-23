package net.tuchnyak.repository;

import net.tuchnyak.model.blog.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface PostRepository {

    void save(Post post);

    void update(Post post);

    boolean existsBySlug(String slug);

    int delete(Post post);

    int deleteById(UUID id);

    List<Post> findAll();

    List<Post> findAllWithoutContent();

    Optional<Post> findBySlug(String slug);

    Optional<Post> findById(UUID slug);

}
