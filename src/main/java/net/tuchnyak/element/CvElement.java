package net.tuchnyak.element;

import net.tuchnyak.service.CvService;
import net.tuchnyak.service.CvServiceImpl;
import net.tuchnyak.util.Logging;
import rife.engine.*;

public class CvElement implements Element, Logging {

    private final CvService cvService;

    public CvElement(CvServiceImpl cvService) {
        this.cvService = cvService;
    }

    public void process(Context c) {
        var cvTemplate = c.template("layout");
        cvTemplate.setBlock("main_content", "cv_include");
        cvTemplate.setBlock("custom_css", "css_cv");
        cvTemplate.setValue("title", "Georgii Shchennikov - CV");

        cvService.processContactInfo(cvTemplate);
        cvService.processSkills(cvTemplate);
        cvService.processWorkExperience(cvTemplate);
        cvService.processEducation(cvTemplate);

        c.print(cvTemplate);
    }

}