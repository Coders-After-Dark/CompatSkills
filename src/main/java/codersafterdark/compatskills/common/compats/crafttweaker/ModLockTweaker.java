package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.ModLock")
@ZenRegister
public class ModLockTweaker {

    @ZenMethod
    public static void addModLock(String modId, String... locked) {
        if (CheckMethods.checkString(modId) && CheckMethods.checkModLoaded(modId) && CheckMethods.checkStringArray(locked)) {
            CompatSkills.LATE_ADDITIONS.add(new Add(modId, locked));
        }
    }

    private static class Add implements IAction {
        String modID;
        String[] requirements;

        Add(String modID, String... requirements) {
            this.modID = modID;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            LevelLockHandler.addModLock(modID, holder);
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }

            return "Setting the requirement of Mod: " + modID + " to: " + descString;
        }
    }
}
