package codersafterdark.compatskills.common.compats.oreexcavator.tweakers;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.oreexcavator.ExcavationShapeKey;
import codersafterdark.compatskills.common.compats.oreexcavator.OreExcavatorCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("oreexcavation")
@ZenClass("mods.compatskills.Excavation")
@ZenRegister
public class ExcavationShapeLockTweaker {
    @ZenMethod
    public static void addShapeLock(String name, String... requirements) {
        if (OreExcavatorCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddShapeLock(name, requirements));
        }
    }

    private static class AddShapeLock implements IAction {
        private final String name;
        private final String[] requirements;

        private AddShapeLock(String name, String... requirements) {
            this.name = name;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new ExcavationShapeKey(name), RequirementHolder.fromStringList(requirements));
                OreExcavatorCompatHandler.registerListener();
            }
        }

        @Override
        public String describe() {
            return "Added Lock for Excavation Shape: " + name + ", With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}
