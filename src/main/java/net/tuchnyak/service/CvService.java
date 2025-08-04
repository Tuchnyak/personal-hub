package net.tuchnyak.service;

import rife.template.Template;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface CvService {

    void processContactInfo(Template cvTemplate);
    void processSkills(Template cvTemplate);
    void processWorkExperience(Template cvTemplate);
    void processEducation(Template cvTemplate);

}
