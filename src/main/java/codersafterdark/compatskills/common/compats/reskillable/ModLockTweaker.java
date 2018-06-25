package codersafterdark.compatskills.common.compats.reskillable;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.ModLock")
@ZenRegister
public class ModLockTweaker {
    @ZenMethod
    public static void addModLock(String modId, String... locked) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new Add(modId, locked));
        }
    }

    private static class Add implements IAction {
        private final String modID;
        private final String[] requirements;

        private Add(String modID, String... requirements) {
            this.modID = modID;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkModLoaded(modID) & CheckMethods.checkStringArray(requirements)) {
                RequirementHolder holder = RequirementHolder.fromStringList(requirements);
                LevelLockHandler.addModLock(modID, holder);
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Setting the requirement of Mod: " + modID + " to Requirements: " + descString;
        }
    }
}