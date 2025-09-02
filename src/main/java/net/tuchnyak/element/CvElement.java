package net.tuchnyak.element;

import net.tuchnyak.service.CvService;
import net.tuchnyak.service.CvServiceImpl;
import rife.engine.*;

public class CvElement extends AbstractLayoutElement {

    private final CvService cvService;

    public CvElement(CvServiceImpl cvService) {
        super("cv_include");
        this.cvService = cvService;
    }

    public void process(Context c) {
        var cvTemplate = getLayoutTemplate(c);
        setCustomCss("css_cv");
        setTitle("George's - CV");

        cvService.processContactInfo(cvTemplate);
        cvService.processSkills(cvTemplate);
        cvService.processWorkExperience(cvTemplate);
        cvService.processEducation(cvTemplate);

        c.print(cvTemplate);
    }

}