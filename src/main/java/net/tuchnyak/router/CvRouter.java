package net.tuchnyak.router;

import net.tuchnyak.element.CvElement;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.service.CvServiceImpl;
import net.tuchnyak.util.Logging;

public class CvRouter extends AbstractRouter implements Logging {

    public CvRouter(CvRepository cvRepository) {
        super(new CvElement(new CvServiceImpl(cvRepository)));
    }

    @Override
    public void setup() {
        super.setup();
        getLogger().info(">>> CV router setup");
    }

}