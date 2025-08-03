package net.tuchnyak.element;/**
 * @author tuchnyak (George Shchennikov)
 */

import net.tuchnyak.model.ContactInfo;
import net.tuchnyak.repository.CvRepository;
import net.tuchnyak.util.Logging;
import rife.engine.*;
import rife.template.Template;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class CvElement implements Element, Logging {

    private static final SimpleDateFormat WORK_EXPERIENCE_DATE_FORMAT = new SimpleDateFormat("MMM. yyyy");

    private final CvRepository cvRepository;

    public CvElement(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public void process(Context c) throws Exception {
        var cvTemplate = c.template("cv");

        List<ContactInfo> contactInfoList = cvRepository.getContactInfoList();
        contactInfoList.stream()
                .filter(ci -> ci.getSort_position() >= 0)
                .forEach(contactInfo -> {
                            cvTemplate.setValue("property", contactInfo.getProperty());
                            cvTemplate.setValue("value", contactInfo.getValue());
                            cvTemplate.appendBlock("contact_info");
                        }
                );
        cvTemplate.setValue(
                "summary",
                contactInfoList.stream()
                        .filter(ci -> ci.getSort_position() == -1)
                        .map(ContactInfo::getValue)
                        .findFirst().orElse("DEFAULT SUMMARY")
        );

        cvRepository.getSkillList().forEach(skill -> {
            cvTemplate.setValue("category", skill.getCategory());
            cvTemplate.setValue("list", skill.getList());
            cvTemplate.appendBlock("skills");
        });

        cvRepository.getWorkExperienceList().stream()
                .sorted((we1, we2) -> we2.getDat().compareTo(we1.getDat()))
                .forEach(we -> {
                            cvTemplate.setValue("company_name", we.getCompany_name());
                            cvTemplate.setValue("location", we.getLocation());
                            cvTemplate.setValue("position", we.getPosition());
                            cvTemplate.setValue("tech_list", we.getTech_list());
                            cvTemplate.setValue("dat", WORK_EXPERIENCE_DATE_FORMAT.format(we.getDat()));
                            cvTemplate.setValue("datto", we.getDatto() == null ? "Present" : WORK_EXPERIENCE_DATE_FORMAT.format(we.getDatto()));
                            parseDescription(we.getDescription(), cvTemplate);
                            cvTemplate.appendBlock("work_experience");
                            cvTemplate.removeValue("description_list");
                        }
                );

        cvRepository.getEducationList().stream()
                .sorted((e1, e2) -> e2.getGraduation_year().compareTo(e1.getGraduation_year()))
                .forEach(education -> {
                            cvTemplate.setValue("name", education.getInstitution_name());
                            cvTemplate.setValue("degree", education.getDegree());
                            cvTemplate.setValue("description", education.getDescription());
                            cvTemplate.setValue("year", education.getGraduation_year());
                            cvTemplate.appendBlock("education");
                        }
                );

        c.print(cvTemplate);
    }

    void parseDescription(String description, Template cvTemplate) {
        String[] descriptionArray = description.split("\\\\n");
        Arrays.stream(descriptionArray)
                .map(String::trim)
                .forEach(descriptionLine -> {
                    cvTemplate.setValue("description_line", descriptionLine);
                    cvTemplate.appendBlock("description_list");
                });
    }

}