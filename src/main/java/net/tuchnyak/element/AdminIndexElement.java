package net.tuchnyak.element;

import rife.engine.Context;
import rife.engine.Element;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AdminIndexElement implements Element {

    @Override
    public void process(Context c) throws Exception {

        c.print("Hello, Admin!");
    }

}
