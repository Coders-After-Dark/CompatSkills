package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTUnlockable;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.actions.LevelUpAction;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.actions.UnlockableAction;
import codersafterdark.compatskills.utils.CheckMethods;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.SkillChange")
@ZenRegister
public class SkillChangeTweaker {//TODO: add a check to see if valid command format?
    @ZenMethod
    public static void addLevelUpCommands(CTSkill skill, int level, String... commands) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkInt(level) & CheckMethods.checkStringArray(commands)) {
            CompatSkills.LATE_ADDITIONS.add(new LevelUpAction.Commands(skill, level, commands));
        }
    }

    @ZenMethod
    public static void addLevelUpHandler(CTSkill skill, int level, IChangeHandler handler) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkInt(level) & CheckMethods.checkChangeHandler(handler)) {
            CompatSkills.LATE_ADDITIONS.add(new LevelUpAction.Handler(skill, level, handler));
        }
    }

    @ZenMethod
    public static void addUnlockableLockCommands(CTUnlockable unlockable, String... commands) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkUnlockable(unlockable.getUnlockable()) & CheckMethods.checkStringArray(commands)) {
            CompatSkills.LATE_ADDITIONS.add(new UnlockableAction.Commands(unlockable, commands, false));
        }
    }

    @ZenMethod
    public static void addUnlockableLockHandler(CTUnlockable unlockable, IChangeHandler handler) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkUnlockable(unlockable.getUnlockable()) & CheckMethods.checkChangeHandler(handler)) {
            CompatSkills.LATE_ADDITIONS.add(new UnlockableAction.Handler(unlockable, handler, false));
        }
    }

    @ZenMethod
    public static void addUnlockableUnlockCommands(CTUnlockable unlockable, String... commands) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkUnlockable(unlockable.getUnlockable()) & CheckMethods.checkStringArray(commands)) {
            CompatSkills.LATE_ADDITIONS.add(new UnlockableAction.Commands(unlockable, commands, true));
        }
    }

    @ZenMethod
    public static void addUnlockableUnlockHandler(CTUnlockable unlockable, IChangeHandler handler) {
        if (ReskillableCompatHandler.ENABLED & CheckMethods.checkUnlockable(unlockable.getUnlockable()) & CheckMethods.checkChangeHandler(handler)) {
            CompatSkills.LATE_ADDITIONS.add(new UnlockableAction.Handler(unlockable, handler, true));
        }
    }
}