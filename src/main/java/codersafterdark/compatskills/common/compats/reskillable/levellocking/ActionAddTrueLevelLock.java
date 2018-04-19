package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.reskillable.api.skill.Skill;

public class ActionAddTrueLevelLock extends ActionAddLevelLock {

    public ActionAddTrueLevelLock(Skill skill, int level, String... defaultRequirements) {
        super(skill, level, defaultRequirements);
    }

    @Override
    public void addToHandler(SkillLock lock) {

    }
}
