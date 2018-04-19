package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.reskillable.customcontent.CrTSkill;
import codersafterdark.compatskills.common.compats.utils.CheckMethods;
import crafttweaker.IAction;

import java.util.Arrays;

public abstract class ActionAddLevelLock implements IAction {
    private final CrTSkill crTSkill;
    private final int level;
    private final String[] defaultRequirements;

    public ActionAddLevelLock(CrTSkill crTSkill, int level, String... defaultRequirements) {
        this.crTSkill = crTSkill;
        this.level = level;
        this.defaultRequirements = defaultRequirements;
    }

    @Override
    public void apply() {
        if (CheckMethods.checkCrTSkillParent(crTSkill) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(defaultRequirements)) {
            addToHandler(new SkillLock(crTSkill, level, defaultRequirements));
        }
    }

    public abstract void addToHandler(SkillLock lock);

    @Override
    public String describe() {
        return "Added Level-Lock " + this.crTSkill + ":" + this.level + " With Requirements: " + Arrays.toString(this.defaultRequirements);
    }

    public CrTSkill getCrTSkill() {
        return crTSkill;
    }

    public int getLevel() {
        return level;
    }

    public String[] getDefaultRequirements() {
        return defaultRequirements;
    }
}
