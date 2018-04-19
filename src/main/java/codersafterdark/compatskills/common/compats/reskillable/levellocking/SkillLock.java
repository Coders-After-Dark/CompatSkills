package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.skill.Skill;

public class SkillLock {
    private final Skill skill;
    private final int level;
    private final RequirementHolder requirementHolder;

    public SkillLock(Skill skill, int level, String... defaultRequirements) {
        this.skill = skill;
        this.level = level;
        this.requirementHolder = RequirementHolder.fromStringList(defaultRequirements);
    }

    public Skill getSkill() {
        return skill;
    }

    public int getLevel() {
        return level;
    }

    public RequirementHolder getRequirementHolder() {
        return requirementHolder;
    }
}
