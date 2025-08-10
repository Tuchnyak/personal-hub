package net.tuchnyak.router;

import net.tuchnyak.element.CvElement;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.service.CvServiceImpl;
import net.tuchnyak.util.Logging;
import rife.engine.*;

public class CvRouter extends Router implements Logging {

    private final CvRepository cvRepository;

    private Route rootRoute;
    private CvServiceImpl cvService;

    public CvRouter(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public void setup() {
        cvService = new CvServiceImpl(cvRepository);
        rootRoute = get("/", PathInfoHandling.NONE, () -> new CvElement(cvService));
        get("", c -> c.redirect(getRootPath()));
        getLogger().info(">>> CV router setup");
    }

    public Route getRootPath() {
        return rootRoute;
    }

}