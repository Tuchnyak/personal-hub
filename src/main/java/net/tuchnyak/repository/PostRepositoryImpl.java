package net.tuchnyak.repository;

import net.tuchnyak.model.blog.Post;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.database.queries.Delete;
import rife.database.queries.Insert;
import rife.database.queries.Select;
import rife.database.queries.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostRepositoryImpl implements PostRepository {

    public static final String BLOG_POSTS_TABLE = "blog.posts";

    private final Datasource datasource;
    private final DbQueryManager dbQueryManager;

    public PostRepositoryImpl(Datasource datasource) {
        this.datasource = datasource;
        this.dbQueryManager = new DbQueryManager(datasource);
    }

    @Override
    public void save(Post post) {
        var insertion = new Insert(datasource)
                .fieldsIncluded(post, new String[]{
                        "id",
                        "slug",
                        "title",
                        "content_html"
                })
                .into(BLOG_POSTS_TABLE);
        dbQueryManager.executeUpdate(insertion);
    }

    @Override
    public void update(Post post) {
        var update = new Update(datasource)
                .table(BLOG_POSTS_TABLE)
                .fields(post);
        dbQueryManager.executeUpdate(update);
    }

    @Override
    public boolean existsBySlug(String slug) {
        var countQuery = new Select(datasource)
                .field("count(*)")
                .where("slug", "=", slug)
                .from(BLOG_POSTS_TABLE);
        int count = dbQueryManager.executeGetFirstInt(countQuery);

        return count != 0;
    }

    @Override
    public int delete(Post post) {
        var deletion = new Delete(datasource)
                .from(BLOG_POSTS_TABLE)
                .where(post);

        return dbQueryManager.executeUpdate(deletion);
    }

    @Override
    public int deleteById(UUID id) {
        var deletion = new Delete(datasource)
                .from(BLOG_POSTS_TABLE)
                .where("id", "=", id);

        return dbQueryManager.executeUpdate(deletion);
    }

    @Override
    public List<Post> findAll() {
        var selection = new Select(datasource)
                .from(BLOG_POSTS_TABLE);

        return dbQueryManager.executeFetchAllBeans(selection, Post.class);
    }

    @Override
    public List<Post> findAllWithoutContent() {
        var selection = new Select(datasource)
                .fieldsExcluded(Post.class, "content_html")
                .from(BLOG_POSTS_TABLE);

        return dbQueryManager.executeFetchAllBeans(selection, Post.class);
    }

    @Override
    public Optional<Post> findBySlug(String slug) {
        var selection = new Select(datasource)
                .from(BLOG_POSTS_TABLE)
                .where("slug", "=", slug);

        return Optional.ofNullable(dbQueryManager.executeFetchFirstBean(selection, Post.class));
    }

    @Override
    public Optional<Post> findById(UUID id) {
        var selection = new Select(datasource)
                .from(BLOG_POSTS_TABLE)
                .where("id", "=", id);

        return Optional.ofNullable(dbQueryManager.executeFetchFirstBean(selection, Post.class));
    }

}
