package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.utils.CheckMethods;
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
    public static void addLevelLock(CTSkill skill, int level, String... defaultRequirements) {
        if (CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(defaultRequirements)) {
            CraftTweakerAPI.apply(new ActionAddTrueLevelLock(skill.getSkill(), level, defaultRequirements));
        }
    }
}
