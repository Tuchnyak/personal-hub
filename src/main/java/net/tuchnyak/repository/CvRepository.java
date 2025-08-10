package net.tuchnyak.repository;

import java.util.List;

import net.tuchnyak.model.cv.ContactInfo;
import net.tuchnyak.model.cv.Education;
import net.tuchnyak.model.cv.Skill;
import net.tuchnyak.model.cv.WorkExperience;

public interface CvRepository {

    public List<ContactInfo> getContactInfoList();

    public List<Skill> getSkillList();

    public List<WorkExperience> getWorkExperienceList();

    public List<Education> getEducationList();

}
