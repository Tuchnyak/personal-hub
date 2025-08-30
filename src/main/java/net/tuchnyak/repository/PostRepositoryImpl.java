package net.tuchnyak.repository;

import net.tuchnyak.db.Transactional;
import net.tuchnyak.model.blog.Post;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.database.queries.Delete;
import rife.database.queries.Insert;
import rife.database.queries.Select;
import rife.database.queries.Update;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class PostRepositoryImpl implements PostRepository, Transactional {

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
                .where("id", "=", post.getId().toString())
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
                .where("id", "=", id.toString());

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

        var post  = new Post();
        var isFound = dbQueryManager.executeFetchFirst(selection, rs -> populatePostByResultSet(rs, post));
        if (!isFound) return Optional.empty();

        return Optional.of(post);
    }

    @Override
    public Optional<Post> findById(UUID id) {
        var selection = new Select(datasource)
                .from(BLOG_POSTS_TABLE)
                .where("id", "=", id.toString());

        var post  = new Post();
        var isFound = dbQueryManager.executeFetchFirst(selection, rs -> populatePostByResultSet(rs, post));
        if (!isFound) return Optional.empty();

        return Optional.of(post);
    }

    @Override
    public DbQueryManager getDbQueryManager() {

        return dbQueryManager;
    }


    private static void populatePostByResultSet(ResultSet rs, Post post) throws SQLException {
        post.setId(UUID.fromString(rs.getString(1)));
        post.setTitle(rs.getString(2));
        post.setSlug(rs.getString(3));
        post.setContent_html(rs.getString(4));
        post.setIs_published(rs.getBoolean(5));
        post.setPublished_at(rs.getTimestamp(6));
        post.setCreated_at(rs.getTimestamp(7));
        post.setUpdated_at(rs.getTimestamp(8));
    }
}
