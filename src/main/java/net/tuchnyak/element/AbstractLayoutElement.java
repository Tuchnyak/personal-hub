package net.tuchnyak.element;

import net.tuchnyak.util.Logging;
import rife.engine.*;
import rife.template.Template;

/**
 * @author tuchnyak (George Shchennikov)
 */
public abstract class AbstractLayoutElement implements Element, Logging {
    private static final String LAYOUT_TEMPLATE_NAME = "layout";

    private final String includeBlockName;
    private Template template;

    protected AbstractLayoutElement(String includeBlockName) {
        this.includeBlockName = includeBlockName;
    }

    @Override
    public abstract void process(Context c) throws Exception;

    void setTitle(String title) {
        template.setValue("title", title);
    }
    void setCustomCss(String cssBlockName) {
        template.setBlock("custom_css", cssBlockName);
    }

    Template activateLayoutTemplate(Context c) {
        template = c.template(LAYOUT_TEMPLATE_NAME);
        template.setBlock("main_content", includeBlockName);

        return template;
    }

}