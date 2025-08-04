package net.tuchnyak.element;

import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.service.CvService;
import net.tuchnyak.service.CvServiceImpl;
import net.tuchnyak.util.Logging;
import rife.engine.*;

public class CvElement implements Element, Logging {

    private final CvService cvService;

    public CvElement(CvRepository cvRepository) {
        this.cvService = new CvServiceImpl(cvRepository);
    }

    public void process(Context c) {
        var cvTemplate = c.template("cv");

        cvService.processContactInfo(cvTemplate);
        cvService.processSkills(cvTemplate);
        cvService.processWorkExperience(cvTemplate);
        cvService.processEducation(cvTemplate);

        c.print(cvTemplate);
    }

}