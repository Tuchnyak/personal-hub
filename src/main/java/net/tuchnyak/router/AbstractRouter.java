package net.tuchnyak.router;

import rife.engine.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
public abstract class AbstractRouter extends Router {

    private Route rootRoute;
    private final Element rootElement;


    public AbstractRouter(Element rootElement) {
        this.rootElement = rootElement;
    }

    public void setup() {
        rootRoute = get("/", PathInfoHandling.NONE, () -> rootElement);
        get("", c -> c.redirect(rootRoute));
    }

    public Route getRootRoute() {
        return rootRoute;
    }

    protected Element getRootElement() {
        return rootElement;
    }

}