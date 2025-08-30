package net.tuchnyak.repository;

import net.tuchnyak.db.AbstractTestDb;
import net.tuchnyak.model.blog.Post;
import net.tuchnyak.uuid.UuidGenerator;
import net.tuchnyak.uuid.UuidGeneratorFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rife.database.queries.Select;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
class PostRepositoryImplTest extends AbstractTestDb {

    private static UuidGenerator idGenerator;

    private PostRepository underTest;
    private UUID fixedUuid;

    @BeforeAll
    static void beforeAllLocal() throws Exception {
        idGenerator = UuidGeneratorFactory.getV7TimeBasedUuidGenerator();
    }

    @BeforeEach
    void setUp() {
        fixedUuid = idGenerator.generate();
        underTest = new PostRepositoryImpl(testDataSource);

        var postToSave = Post.builder()
                .withId(fixedUuid)
                .withTitle("Title before each")
                .withSlug("/before_each")
                .withContentHtml("Test content")
                .build();
        underTest.save(postToSave);
    }

    @AfterEach
    void tearDown() {
        deleteAll();
    }


    @Test
    void save() {
        deleteAll();

        var postToSave = getPostToSave();
        underTest.save(postToSave);

        var savedPosts = queryManager.executeFetchAllBeans(new Select(testDataSource).from("blog.posts"), Post.class);

        assertNotNull(savedPosts);
        assertEquals(1, savedPosts.size());

        var postFromDb = savedPosts.get(0);
        assertEquals(postToSave.getTitle(), postFromDb.getTitle());
        assertEquals(postToSave.getSlug(), postFromDb.getSlug());
        assertEquals(postToSave.getContent_html(), postFromDb.getContent_html());
        assertEquals(postToSave.getId(), postFromDb.getId());
        assertFalse(postFromDb.isIs_published());
        assertNull(postFromDb.getPublished_at());
        assertNotNull(postFromDb.getCreated_at());
        assertNotNull(postFromDb.getUpdated_at());
        assertEquals(postFromDb.getCreated_at(), postFromDb.getUpdated_at());
    }

    @Test
    void update() {
        Timestamp now = Timestamp.from(Instant.now());

        var postFromDb = underTest.findById(fixedUuid).orElseThrow();
        postFromDb.setTitle("Updated title");
        postFromDb.setIs_published(true);
        postFromDb.setPublished_at(now);
        postFromDb.setUpdated_at(now);
        var createdAt = postFromDb.getCreated_at();

        underTest.update(postFromDb);

        var updatedPost = underTest.findById(fixedUuid).orElseThrow();
        assertEquals("Updated title", updatedPost.getTitle());
        assertTrue(updatedPost.isIs_published());
        assertEquals(now.toString(), updatedPost.getPublished_at().toString());
        assertEquals(now.toString(), updatedPost.getUpdated_at().toString());
        assertEquals(createdAt, updatedPost.getCreated_at());
    }

    @Test
    void existsBySlug() {
        boolean actual = underTest.existsBySlug("/before_each");
        assertTrue(actual);

        actual = underTest.existsBySlug("/not_exists");
        assertFalse(actual);
    }

    @Test
    void delete() {
        var postToSave = getPostToSave();
        underTest.save(postToSave);
        assertEquals(2, underTest.findAll().size());

        var deleted = underTest.delete(postToSave);

        assertEquals(1, deleted);
        assertEquals(1, underTest.findAll().size());
        assertEquals(fixedUuid, underTest.findAll().get(0).getId());
    }

    @Test
    void deleteById() {
        var post = getPostToSave();
        var id = post.getId();
        underTest.save(post);

        var deleted = underTest.deleteById(fixedUuid);

        assertEquals(1, deleted);
        assertEquals(1, underTest.findAll().size());
        assertEquals(id, underTest.findAll().get(0).getId());
    }

    @Test
    void findAll() {
        underTest.save(getPostToSave());

        var actualList = underTest.findAll();

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
        var setOfSlugs = Set.of("/before_each", "/slug");
        actualList.forEach(post -> {
                    assertTrue(setOfSlugs.contains(post.getSlug()));
                }
        );
    }

    @Test
    void findAllWithoutContent() {
        underTest.save(getPostToSave());
        var actualList = underTest.findAllWithoutContent();

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
        var setOfSlugs = Set.of("/before_each", "/slug");
        actualList.forEach(post -> {
                    assertTrue(setOfSlugs.contains(post.getSlug()));
                    assertNull(post.getContent_html());
                }
        );
    }

    @Test
    void findBySlug() {
        var actual = underTest.findBySlug("/before_each");

        assertTrue(actual.isPresent());
        assertEquals(fixedUuid, actual.get().getId());
        assertEquals("/before_each", actual.get().getSlug());
        assertEquals("Title before each", actual.get().getTitle());
        assertEquals("Test content", actual.get().getContent_html());
    }

    @Test
    void findById() {
        var actual = underTest.findById(fixedUuid);

        assertTrue(actual.isPresent());
        assertEquals(fixedUuid, actual.get().getId());
        assertEquals("/before_each", actual.get().getSlug());
        assertEquals("Title before each", actual.get().getTitle());
        assertEquals("Test content", actual.get().getContent_html());
    }


    private @NotNull Post getPostToSave() {
        return Post.builder()
                .withId(idGenerator.generate())
                .withTitle("Title")
                .withSlug("/slug")
                .withContentHtml("Content")
                .withIsPublished(false)
                .build();
    }

    private void deleteAll() {
        queryManager.executeUpdate("DELETE FROM blog.posts");
    }

}