package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
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
    public static void addEMCLock(long emc, String... locked) {
        if (ProjectECompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddEMCLock(emc, locked));
        }
    }

    private static class AddEMCLock implements IAction {
        private final long emc;
        private final String[] requirements;

        private AddEMCLock(long emc, String... requirements) {
            this.emc = emc;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkLong(emc) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new EMCLockKey(emc), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Setting the requirement of items with emc value: " + emc + " or higher to Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}