package codersafterdark.compatskills.common.compats.reskillable.skillhiding;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.VisibilityLock")
@ZenRegister
public class VisibilityLockTweaker {
    @ZenMethod
    public static void addVisibilityLock(CTSkill skill, String... defaultRequirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddVisibilityLock(skill, defaultRequirements));
    }

    private static class AddVisibilityLock implements IAction {
        private final CTSkill skill;
        private final String[] requirements;

        AddVisibilityLock(CTSkill skill, String... requirements) {
            this.skill = skill;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkStringArray(requirements)) {
                RequirementHolder holder = RequirementHolder.fromStringList(requirements);
                LevelLockHandler.addLockByKey(new VisibilityLock(skill.getSkill()), holder);
            }
        }

        @Override
        public String describe() {
            return null;
        }
    }
}
