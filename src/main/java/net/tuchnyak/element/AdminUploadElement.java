package net.tuchnyak.element;

import rife.engine.Context;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AdminUploadElement extends AbstractLayoutElement {

    public AdminUploadElement() {
        super("admin_upload_include");
    }

    @Override
    public void process(Context c) throws Exception {
        var template = activateLayoutTemplate(c);
        setTitle("Admin: Upload post");

        c.print(template);
    }

}
