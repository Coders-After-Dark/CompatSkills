package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTUnlockable;
import codersafterdark.compatskills.utils.CheckMethods;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.SkillChange")
@ZenRegister
public class SkillChangeTweaker {//TODO: add a check to see if valid command format?
    @ZenMethod
    public static void addLevelUpCommands(CTSkill skill, int level, String... commands) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(commands)) {
            CompatSkills.LATE_ADDITIONS.add(new AddLevelUpCommand(skill, level, commands));
        }
    }

    @ZenMethod
    public static void addUnlockableLockCommands(CTUnlockable unlockable, String... commands) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkUnlockable(unlockable.getUnlockable()) & CheckMethods.checkStringArray(commands)) {
            CompatSkills.LATE_ADDITIONS.add(new AddLockCommands(unlockable, commands));
        }
    }

    @ZenMethod
    public static void addUnlockableUnlockCommands(CTUnlockable unlockable, String... commands) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkUnlockable(unlockable.getUnlockable()) & CheckMethods.checkStringArray(commands)) {
            CompatSkills.LATE_ADDITIONS.add(new AddUnlockCommands(unlockable, commands));
        }
    }

    private static class AddLevelUpCommand implements IAction {
        private final CTSkill skill;
        private final int level;
        private final String[] commands;

        private AddLevelUpCommand(CTSkill skill, int level, String... commands) {
            this.skill = skill;
            this.level = level;
            this.commands = commands;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(new SkillLevel(skill.getSkill(), level), commands);
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(commands).map(string -> string + ", ").collect(Collectors.joining());
            return "Added commands to fire when " + skill.getName() + " reaches level " + level + ". Commands: " + descString;
        }
    }

    private static class AddLockCommands implements IAction {
        private final CTUnlockable unlockable;
        private final String[] commands;

        private AddLockCommands(CTUnlockable unlockable, String... commands) {
            this.unlockable = unlockable;
            this.commands = commands;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(new UnlockableLock(unlockable.getUnlockable()), commands);
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(commands).map(string -> string + ", ").collect(Collectors.joining());
            return "Added commands to fire when " + unlockable.getName() + " is locked. Commands: " + descString;
        }
    }

    private static class AddUnlockCommands implements IAction {
        private final CTUnlockable unlockable;
        private final String[] commands;

        private AddUnlockCommands(CTUnlockable unlockable, String... commands) {
            this.unlockable = unlockable;
            this.commands = commands;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(new UnlockableUnlock(unlockable.getUnlockable()), commands);
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(commands).map(string -> string + ", ").collect(Collectors.joining());
            return "Added commands to fire when " + unlockable.getName() + " is unlocked. Commands: " + descString;
        }
    }
}