package net.tuchnyak.service;

import net.tuchnyak.db.AbstractTestDb;
import net.tuchnyak.dto.Page;
import net.tuchnyak.dto.PostListItem;
import net.tuchnyak.exception.PersonalHubException;
import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.model.blog.Post;
import net.tuchnyak.repository.PostRepositoryImpl;
import net.tuchnyak.util.TimestampSqlNow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
class PostUploadServiceImplTest extends AbstractTestDb {

    private static String aboutMdFileContent;

    private PostUploadService underTest;
    private PostRepositoryImpl repositorySpied;

    @BeforeAll
    static void beforeAll() throws Exception {
        aboutMdFileContent = resourceFinder.getContent(resourceFinder.getResource("md/about.md"), StandardCharsets.UTF_8.displayName());
    }

    @BeforeEach
    void setUp() {
        repositorySpied = Mockito.spy(new PostRepositoryImpl(testDataSource));
        underTest = new PostUploadServiceImpl(repositorySpied);
    }

    @AfterEach
    void tearDown() {
        queryManager.executeUpdate("DELETE FROM blog.posts");
    }

    @Test
    void shouldUploadPost() {
        Post savedPost = underTest.uploadPost(aboutMdFileContent);

        Mockito.verify(repositorySpied).save(savedPost);
        Optional<Post> post = repositorySpied.findBySlug("/about");
        assertTrue(post.isPresent());
        assertEquals(savedPost.getId(), post.get().getId());
    }

    @Test
    void shouldPublishPost() {
        var savedPostId = underTest.uploadPost(aboutMdFileContent).getId();
        assertFalse(repositorySpied.findById(savedPostId).get().isIs_published());

        underTest.publishPost(savedPostId);
        var nowTimestamp = new TimestampSqlNow().now();

        var actual = repositorySpied.findById(savedPostId).orElseThrow();
        assertTrue(actual.isIs_published());
        assertNotNull(actual.getPublished_at());
        assertTrue(actual.getPublished_at().before(nowTimestamp));
        assertTrue(actual.getCreated_at().before(nowTimestamp));
        assertTrue(actual.getUpdated_at().before(nowTimestamp));
    }

    @Test
    void shouldRollbackPublishPost() {
        Mockito.doThrow(new RuntimeException("Something went wrong"))
                .when(repositorySpied)
                .update(Mockito.any(Post.class));

        var savedPostId = underTest.uploadPost(aboutMdFileContent).getId();
        assertFalse(repositorySpied.findById(savedPostId).get().isIs_published());

        assertThrows(PersonalHubException.class, () -> underTest.publishPost(savedPostId));

        var actual = repositorySpied.findById(savedPostId).orElseThrow();
        assertFalse(actual.isIs_published());
        assertNull(actual.getPublished_at());
    }

    @Test
    void shouldReplaceBySlug() {
        var firstPost = underTest.uploadPost(aboutMdFileContent);
        assertNotNull(firstPost);

        var newId = underTest.uploadByReplace(aboutMdFileContent, "/about");
        assertNotNull(newId);
        assertNotEquals(firstPost.getId(), newId);
    }

    @Test
    void shouldReplaceBySlugWhenNoPreviousPost() {
        var newId = underTest.uploadByReplace(aboutMdFileContent, "/about");
        assertNotNull(newId);
    }

    @Test
    void shouldGetFirstPageOfPublishedPostListPaginated() {
        IntStream.rangeClosed(1, 15).forEach(i -> repositorySpied.save(getPostWithSlug(i, i < 13)));

        int pageNumber = 1;
        int pageSize = 5;
        Page<PostListItem> actualPage = underTest.getPublishedPostListPaginated(pageNumber, pageSize);

        assertNotNull(actualPage);
        assertFalse(actualPage.items().isEmpty());
        assertEquals(5, actualPage.items().size());
        assertEquals(1, actualPage.currentPage());
        assertEquals(12, actualPage.totalItems());
        assertEquals(3, actualPage.totalPages());
        assertTrue(actualPage.hasNext());
        assertFalse(actualPage.hasPrevious());

        var items = actualPage.items();
        for (int i = 0, j = 12; i < 5; i++, j--) {
            assertEquals("Test_" + j, items.get(i).getTitle());
        }
    }

    @Test
    void shouldGetSecondPageOfPublishedPostListPaginated() {
        IntStream.rangeClosed(1, 15).forEach(i -> repositorySpied.save(getPostWithSlug(i, i < 13)));

        int pageNumber = 2;
        int pageSize = 5;
        Page<PostListItem> actualPage = underTest.getPublishedPostListPaginated(pageNumber, pageSize);

        assertNotNull(actualPage);
        assertFalse(actualPage.items().isEmpty());
        assertEquals(5, actualPage.items().size());
        assertEquals(2, actualPage.currentPage());
        assertEquals(12, actualPage.totalItems());
        assertEquals(3, actualPage.totalPages());
        assertTrue(actualPage.hasNext());
        assertTrue(actualPage.hasPrevious());

        var items = actualPage.items();
        for (int i = 0, j = 7; i < 5; i++, j--) {
            assertEquals("Test_" + j, items.get(i).getTitle());
        }
    }

    @Test
    void shouldGetThirdPageOfPublishedPostListPaginated() {
        IntStream.rangeClosed(1, 15).forEach(i -> repositorySpied.save(getPostWithSlug(i, i < 13)));

        int pageNumber = 3;
        int pageSize = 5;
        Page<PostListItem> actualPage = underTest.getPublishedPostListPaginated(pageNumber, pageSize);

        assertNotNull(actualPage);
        assertFalse(actualPage.items().isEmpty());
        assertEquals(2, actualPage.items().size());
        assertEquals(3, actualPage.currentPage());
        assertEquals(12, actualPage.totalItems());
        assertEquals(3, actualPage.totalPages());
        assertFalse(actualPage.hasNext());
        assertTrue(actualPage.hasPrevious());

        var items = actualPage.items();
        for (int i = 0, j = 2; i < 2; i++, j--) {
            assertEquals("Test_" + j, items.get(i).getTitle());
        }
    }

    @Test
    void shouldThrowExceptionWhenPageIsGreaterThanTotalPages() {
        IntStream.rangeClosed(1, 15).forEach(i -> repositorySpied.save(getPostWithSlug(i, i < 13)));

        int pageNumber = 4;
        int pageSize = 5;
        assertThrows(PostNotFoundException.class, () -> underTest.getPublishedPostListPaginated(pageNumber, pageSize));
    }


    private Post getPostWithSlug(int count, boolean isPublished) {
        var now = new TimestampSqlNow().now();
        return Post.builder()
                .withId(UUID.randomUUID())
                .withTitle("Test_" + count)
                .withSlug("/slug_" + count)
                .withContentHtml("Test content")
                .withIsPublished(isPublished)
                .withCreatedAt(now)
                .withUpdatedAt(now)
                .withPublishedAt(isPublished ? now : null)
                .build();
    }

}