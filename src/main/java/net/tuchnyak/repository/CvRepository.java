package net.tuchnyak.repository;

import java.util.List;

import net.tuchnyak.model.*;

public interface CvRepository {

    public List<ContactInfo> getContactInfoList();

    public List<Skill> getSkillList();

    public List<WorkExperience> getWorkExperienceList();

    public List<Education> getEducationList();

}
