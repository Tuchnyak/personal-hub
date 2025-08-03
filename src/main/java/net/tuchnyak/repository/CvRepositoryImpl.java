package net.tuchnyak.repository;

import java.util.Collections;
import java.util.List;

import net.tuchnyak.model.ContactInfo;
import net.tuchnyak.model.Education;
import net.tuchnyak.model.Skill;
import net.tuchnyak.model.WorkExperience;
import net.tuchnyak.util.Logging;
import org.hsqldb.lib.StringUtil;
import rife.database.Datasource;
import rife.database.DbQueryManager;
import rife.database.exceptions.DatabaseException;
import rife.database.queries.Select;

public class CvRepositoryImpl implements CvRepository, Logging {

    private final Datasource datasource;
    private final DbQueryManager dbQueryManager;

    public CvRepositoryImpl(Datasource datasource) {
        this.datasource = datasource;
        this.dbQueryManager = new DbQueryManager(datasource);
    }

    @Override
    public List<ContactInfo> getContactInfoList() {
        try {

            return dbQueryManager.executeFetchAllBeans(getSelect("cv.contact_info", "sort_position"), ContactInfo.class);
        } catch (DatabaseException exception) {
            getLogger().error("Error while fetching contact info", exception);
           return Collections.emptyList();
        }
    }

    @Override
    public List<Skill> getSkillList() {
        try {

            return dbQueryManager.executeFetchAllBeans(getSelect("cv.skills", "sort_position"), Skill.class);
        } catch (DatabaseException exception) {
            getLogger().error("Error while fetching skills", exception);
            return Collections.emptyList();
        }
    }

    @Override
    public List<WorkExperience> getWorkExperienceList() {
        try {

            return dbQueryManager.executeFetchAllBeans(getSelect("cv.work_experience", "dat"), WorkExperience.class);
        } catch (DatabaseException exception) {
            getLogger().error("Error while fetching work experience", exception);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Education> getEducationList() {
        try {

            return dbQueryManager.executeFetchAllBeans(getSelect("cv.education", "graduation_year"), Education.class);
        } catch (DatabaseException exception) {
            getLogger().error("Error while fetching education", exception);
            return Collections.emptyList();
        }
    }

    Select getSelect(String tableName, String orderBy) {
        var selectQuery = new Select(datasource).from(tableName);
        if (!StringUtil.isEmpty(orderBy)) {
            selectQuery.orderBy(orderBy);
        }
        return selectQuery;
    }

}
