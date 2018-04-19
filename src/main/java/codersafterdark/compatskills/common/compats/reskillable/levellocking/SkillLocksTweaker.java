package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.reskillable.customcontent.CrTSkill;
import codersafterdark.compatskills.common.compats.utils.CheckMethods;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.SkillLocks")
@ZenRegister
public class SkillLocksTweaker {
    @ZenMethod
    public static void addLevelLock(CrTSkill skill, int level, String... defaultRequirements) {
        if (CheckMethods.checkCrTSkillParent(skill) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(defaultRequirements)) {
            CraftTweakerAPI.apply(new ActionAddTrueLevelLock(skill, level, defaultRequirements));
        }
    }
}
