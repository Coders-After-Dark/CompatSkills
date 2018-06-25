package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.SkillLocks")
@ZenRegister
public class SkillLocksTweaker {
    @ZenMethod
    public static void addLevelLock(CTSkill skill, int level, String... defaultRequirements) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(defaultRequirements)) {
            CompatSkills.LATE_ADDITIONS.add(new AddLevelLock(skill, level, defaultRequirements));
        }
    }

    private static class AddLevelLock implements IAction {
        private final CTSkill skill;
        private final int level;
        private final String[] requirements;

        private AddLevelLock(CTSkill skill, int level, String... requirements) {
            this.skill = skill;
            this.level = level;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            ReskillableCompatHandler.addReskillableLock(new SkillLock(skill.getSkill(), level), RequirementHolder.fromStringList(requirements));
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Level-Lock " + (skill == null ? "null" : skill.getName()) + ": " + level + " With Requirements: " + descString;
        }
    }
}