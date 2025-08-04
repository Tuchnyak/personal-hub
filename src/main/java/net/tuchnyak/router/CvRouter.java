package net.tuchnyak.router;

import net.tuchnyak.element.CvElement;
import net.tuchnyak.repository.CvRepository;
import rife.engine.*;

public class CvRouter extends Router {

    private final CvRepository cvRepository;
    private Route rootRoute;

    public CvRouter(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public void setup() {
        rootRoute = get("/", PathInfoHandling.NONE, () -> new CvElement(cvRepository));
        get("", c -> c.redirect(getRootPath()));
    }

    public Route getRootPath() {
        return rootRoute;
    }

}