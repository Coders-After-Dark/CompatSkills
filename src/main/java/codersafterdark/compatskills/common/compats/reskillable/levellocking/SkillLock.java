package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.reskillable.customcontent.CrTSkill;
import codersafterdark.reskillable.api.data.RequirementHolder;

public class SkillLock {
    private final CrTSkill crTSkill;
    private final int level;
    private final RequirementHolder requirementHolder;

    public SkillLock(CrTSkill crTSkill, int level, String... defaultRequirements) {
        this.crTSkill = crTSkill;
        this.level = level;
        this.requirementHolder = RequirementHolder.fromStringList(defaultRequirements);
    }

    public CrTSkill getCrTSkill() {
        return crTSkill;
    }

    public int getLevel() {
        return level;
    }

    public RequirementHolder getRequirementHolder() {
        return requirementHolder;
    }
}
