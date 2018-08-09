package codersafterdark.compatskills.common.compats.minecraft.item.food;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.FoodTweaker")
@ZenRegister
public class FoodTweaker {
    @ZenMethod
    public static void addHungerLock(int level, String... requirements) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddHungerLock(level, requirements));
        }
    }

    @ZenMethod
    public static void addSaturationLock(float level, String... requirements) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddSaturationLock(level, requirements));
        }
    }

    private static class AddSaturationLock implements IAction {
        private final float level;
        private final String[] requirements;

        private AddSaturationLock(float level, String... requirements) {
            this.level = level;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkFloat(level) & CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new SaturationLockKey(level), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Food Lock for items of saturation level: " + level + ", With Requirements: " + Utils.formatRequirements(requirements);
        }
    }

    private static class AddHungerLock implements IAction {
        private final int level;
        private final String[] requirements;

        private AddHungerLock(int level, String... requirements) {
            this.level = level;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkInt(level) & CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new HungerLockKey(level), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Food Lock for items of hunger level: " + level + ", With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}