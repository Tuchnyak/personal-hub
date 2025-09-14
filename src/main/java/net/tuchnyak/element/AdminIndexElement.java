package net.tuchnyak.element;

import rife.engine.Context;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AdminIndexElement extends AbstractLayoutElement {

    public AdminIndexElement() {
        super("admin_index_include");
    }

    @Override
    public void process(Context c) throws Exception {
        var template = activateLayoutTemplate(c);
        setTitle("Admin index");

        c.print(template);
    }

}
