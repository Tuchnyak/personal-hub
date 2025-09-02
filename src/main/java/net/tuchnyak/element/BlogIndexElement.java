package net.tuchnyak.element;

import net.tuchnyak.dto.Page;
import net.tuchnyak.dto.PostListItem;
import net.tuchnyak.exception.post.PostNotFoundException;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.BlockAppendHandler;
import rife.engine.Context;
import rife.template.Template;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlogIndexElement extends AbstractLayoutElement {
    public static final int ONE = 1;
    private static final int PAGE_SIZE = 10;

    private final PostUploadService postService;

    public BlogIndexElement(PostUploadService postService) {
        super("blog_index_include");
        this.postService = postService;
    }

    @Override
    public void process(Context c) throws Exception {
        int pageNumber = getPageNumber(c);
        Page<PostListItem> page = getPostListItemPage(pageNumber);

        var blogIndexTemplate = getLayoutTemplate(c);
        String title = "George's - BLog";
        setTitle(title);

        if (page.items().isEmpty()) {
            blogIndexTemplate.setBlock("list_content", "empty_list_block");
            c.print(blogIndexTemplate);
            return;
        }

        var blockAppender = new BlockAppendHandler(blogIndexTemplate);
        page.items().forEach(
                postItem -> {
                    blogIndexTemplate.setValue("post_title", postItem.getTitle());
                    blogIndexTemplate.setValue("post_publish_date", postItem.getPublished_at());
                    blogIndexTemplate.setValue("post_slug", postItem.getSlug());
                    blockAppender.setOrAppend("list_content", "post_list_item");
                }
        );
        blogIndexTemplate.setValue("total_posts", page.totalItems());
        blogIndexTemplate.setValue("current_page", page.currentPage());
        setTitle(title + " (%S)".formatted(page.currentPage()));

        handlePrevAndNextBlocks(page, blogIndexTemplate);

        c.print(blogIndexTemplate);
    }

    private Page<PostListItem> getPostListItemPage(int pageNumber) {
        Page<PostListItem> page;
        try {
            page = postService.getPublishedPostListPaginated(pageNumber, PAGE_SIZE);
        } catch (PostNotFoundException e) {
            getLogger().error(">>> Error during page data fetching! Page number: %d".formatted(pageNumber), e);
            page = Page.empty();
        }
        return page;
    }

    private void handlePrevAndNextBlocks(Page<PostListItem> page, Template blogIndexTemplate) {
        if (page.hasPrevious()) {
            blogIndexTemplate.setBlock("prev_content", "prev_block");
            blogIndexTemplate.setValue("prev_page_number", page.currentPage() - ONE);
            blogIndexTemplate.setValue("first_page_number", ONE);
        }
        if (page.hasNext()) {
            blogIndexTemplate.setBlock("next_content", "next_block");
            blogIndexTemplate.setValue("next_page_number", page.currentPage() + ONE);
            blogIndexTemplate.setValue("last_page_number", page.totalPages());
        }
    }

    private int getPageNumber(Context c) {
        var rawPageNumber = c.parameter("page_number");
        int pageNumber;
        if (rawPageNumber == null || Integer.parseInt(rawPageNumber) < ONE) {
            pageNumber = ONE;
        } else {
            pageNumber = Integer.parseInt(rawPageNumber);
        }

        return pageNumber;
    }

}
