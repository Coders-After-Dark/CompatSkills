package codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ZenClass("mods.compatskills.HarvestLock")
@ZenRegister
public class HarvestLevelTweaker {
    @ZenMethod
    public static void addToolLevelLock(int level, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddToolLevelLock(null, level, requirements));
    }

    @ZenMethod
    public static void addToolLevelLock(String type, int level, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddToolLevelLock(type, level, requirements));
    }

    @ZenMethod
    public static void addBlockLevelLock(int level, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddBlockLevelLock(level, requirements));
    }

    private static class AddToolLevelLock implements IAction {
        private final String type;
        private final int harvestLevel;
        private final String[] requirements;

        private AddToolLevelLock(String type, int harvestLevel, String... requirements) {
            this.type = type;
            this.harvestLevel = harvestLevel;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkInt(harvestLevel) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new ToolHarvestLock(type, harvestLevel), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Harvest Level Lock for tools " + (type == null ? "" :  "of type: " + type) +
                    " with harvest level: " + harvestLevel + ", With Requirements: " + descString;
        }
    }

    private static class AddBlockLevelLock implements IAction {
        private final int harvestLevel;
        private final String[] requirements;

        private AddBlockLevelLock(int harvestLevel, String... requirements) {
            this.harvestLevel = harvestLevel;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkInt(harvestLevel) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new BlockHarvestLock(harvestLevel), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Harvest Level Lock for blocks of harvest level: " + harvestLevel + ", With Requirements: " + descString;
        }
    }
}