package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("projecte")
@ZenClass("mods.compatskills.EMCLock")
@ZenRegister
public class ProjectETweaker {

    @ZenMethod
    public static void addEMCLock(int emc, String... locked) {
        if (CheckMethods.checkInt(emc) && CheckMethods.checkStringArray(locked)) {
            CompatSkills.LATE_ADDITIONS.add(new AddEMCLock(emc, locked));
        }
    }

    private static class AddEMCLock implements IAction {
        int emc;
        String[] requirements;

        AddEMCLock(int emc, String... requirements) {
            this.emc = emc;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            LevelLockHandler.addLockByKey(new EMCLockKey(emc), RequirementHolder.fromStringList(requirements));
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }

            return "Setting the requirement of items with emc value: " + emc + " or higher to: " + descString;
        }
    }
}