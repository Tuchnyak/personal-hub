package net.tuchnyak.element;

import net.tuchnyak.dto.PostListItem;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.BlockAppendHandler;
import rife.engine.Context;
import rife.template.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AdminIndexElement extends AbstractLayoutElement {

    private final PostUploadService postService;

    public AdminIndexElement(PostUploadService postService) {
        super("admin_index_include");
        this.postService = postService;
    }

    @Override
    public void process(Context c) throws Exception {
        var template = activateLayoutTemplate(c);
        setTitle("Admin: Index");

        List<PostListItem> drafts = new ArrayList<>();
        List<PostListItem> published = new ArrayList<>();
        postService.getAllPostsItems().forEach(item -> {
            if (item.getPublished_at() == null) {
                drafts.add(item);
            } else {
                published.add(item);
            }
        });

        populateDraftSection(drafts, template);
        populatePublishedSection(published, template);

        c.print(template);
    }

    private void populateDraftSection(List<PostListItem> drafts, Template template) {
        if (drafts.isEmpty()) {
            template.setBlock("draft_content", "empty_list_block");
        } else {
            var blockAppender = new BlockAppendHandler(template);
            drafts.forEach(draft -> {
                template.setValue("draft_title",draft.getTitle());
                template.setValue("draft_slug",draft.getSlug());
                template.setValue("draft_id", draft.getId());
                blockAppender.setOrAppend("draft_content", "draft_item_block");
            });
        }
    }

    private void populatePublishedSection(List<PostListItem> published, Template template) {
        if (published.isEmpty()) {
            template.setBlock("published_content", "empty_list_block");
        } else {
            var blockAppender = new BlockAppendHandler(template);
            published.forEach(post -> {
                template.setValue("post_title",post.getTitle());
                template.setValue("post_slug",post.getSlug());
                template.setValue("post_pub_date", post.getPublished_at());
                template.setValue("post_id", post.getId());
                blockAppender.setOrAppend("published_content", "post_item_block");
            });
        }
    }

}
