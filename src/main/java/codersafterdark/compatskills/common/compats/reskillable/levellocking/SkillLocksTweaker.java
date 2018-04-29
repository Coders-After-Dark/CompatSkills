package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
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
        StringBuilder descString = new StringBuilder("Requirements: ");

        if (CheckMethods.checkStringArray(defaultRequirements)) {
            for (String string : defaultRequirements) {
                descString.append(string).append(", ");
            }
        }

        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                if (CheckMethods.checkSkill(skill.getSkill()) && CheckMethods.checkInt(level) && CheckMethods.checkStringArray(defaultRequirements)) {
                    RequirementHolder holder = RequirementHolder.fromStringList(defaultRequirements);
                    LevelLockHandler.addLockByKey(new SkillLock(skill.getSkill(), level), holder);
                }
            }

            @Override
            public String describe() {
                return "Added Level-Lock " + skill.getName() + ": " + level + " With Requirements: " + descString;
            }
        });
    }
}
