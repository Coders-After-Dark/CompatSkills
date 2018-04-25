package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.utils.CheckMethods;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.IAction;

import java.util.Arrays;

public abstract class ActionAddLevelLock implements IAction {
    private final Skill skill;
    private final int level;
    private final String[] defaultRequirements;

    public ActionAddLevelLock(Skill skill, int level, String... defaultRequirements) {
        this.skill = skill;
        this.level = level;
        this.defaultRequirements = defaultRequirements;
    }

    @Override
    public void apply() {
        if (CheckMethods.checkSkill(skill) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(defaultRequirements)) {
            addToHandler(new SkillLock(skill, level, defaultRequirements));
        }
    }

    public abstract void addToHandler(SkillLock lock);

    @Override
    public String describe() {
        return "Added Level-Lock " + this.skill.getName() + ":" + this.level + " With Requirements: " + Arrays.toString(this.defaultRequirements);
    }

    public Skill getSkill() {
        return skill;
    }

    public int getLevel() {
        return level;
    }

    public String[] getDefaultRequirements() {
        return defaultRequirements;
    }
}
