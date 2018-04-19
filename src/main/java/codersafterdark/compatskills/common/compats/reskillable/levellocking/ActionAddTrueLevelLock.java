package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.reskillable.customcontent.CrTSkill;

public class ActionAddTrueLevelLock extends ActionAddLevelLock{

    public ActionAddTrueLevelLock(CrTSkill skill, int level, String... defaultRequirements) {
        super(skill, level, defaultRequirements);
    }

    @Override
    public void addToHandler(SkillLock lock) {

    }
}
