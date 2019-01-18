package codersafterdark.compatskills.common.compats.oreexcavator.tweakers;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.oreexcavator.OreExcavationCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("oreexcavation")
@ZenClass("mods.compatskills.Excavation")
@ZenRegister
public class ExcavationLockTweaker {
    @ZenMethod
    public static void addExcavationLock(String... requirements) {
        if (OreExcavationCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddExcavationLock(requirements));
        }
    }

    private static class AddExcavationLock implements IAction {
        private final String[] requirements;

        private AddExcavationLock(String... requirements) {
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkStringArray(requirements)) {
                OreExcavationCompatHandler.addOERequirements(RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Lock for Ore Excavation, With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}
