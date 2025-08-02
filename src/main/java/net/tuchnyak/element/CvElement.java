package net.tuchnyak.element;/**
 * @author tuchnyak (George Shchennikov)
 */

import net.tuchnyak.repository.CvRepository;
import rife.engine.*;

public class CvElement implements Element {

    private final CvRepository cvRepository;

    public CvElement(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public void process(Context c) throws Exception {
        var cvTemplate = c.template("cv");

        cvTemplate.setBean(cvRepository.getContactInfoList(), "contact_info", false);
        cvTemplate.setBean(cvRepository.getSkillList(), "skills", false);
        cvTemplate.setBean(cvRepository.getWorkExperienceList(), "work_experience", false);
        cvTemplate.setBean(cvRepository.getEducationList(), "education", false);

        c.print(cvTemplate);
    }

}