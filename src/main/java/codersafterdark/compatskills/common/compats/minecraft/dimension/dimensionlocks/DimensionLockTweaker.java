package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.DimensionLock")
@ZenRegister
public class DimensionLockTweaker {
    @ZenMethod
    public static void addDimensionLock(int dimension, String... defaultRequirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddDimensionLock(dimension, defaultRequirements));
    }

    private static class AddDimensionLock implements IAction {
        private final int dimension;
        private final String[] requirements;

        private AddDimensionLock(int dimension, String... requirements) {
            this.dimension = dimension;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidDimension(dimension) && CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new DimensionLockKey(dimension), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Dimension Lock for Dimension: " + dimension + ", With " + descString;
        }
    }
}
