package net.tuchnyak.router;

import net.tuchnyak.element.CvElement;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.service.CvServiceImpl;
import rife.engine.*;

public class CvRouter extends Router {

    private final CvRepository cvRepository;
    private Route rootRoute;

    public CvRouter(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public void setup() {
        var cvService = new CvServiceImpl(cvRepository);
        rootRoute = get("/", PathInfoHandling.NONE, () -> new CvElement(cvService));
        get("", c -> c.redirect(getRootPath()));
    }

    public Route getRootPath() {
        return rootRoute;
    }

}