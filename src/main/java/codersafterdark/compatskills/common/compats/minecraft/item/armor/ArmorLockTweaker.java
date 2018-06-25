package codersafterdark.compatskills.common.compats.minecraft.item.armor;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ZenClass("mods.compatskills.ArmorLock")
@ZenRegister
public class ArmorLockTweaker {
    @ZenMethod
    public static void addArmorLock(double armor, String... requirements) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddArmorLock(armor, requirements));
        }
    }

    private static class AddArmorLock implements IAction {
        private final double armor;
        private final String[] requirements;

        private AddArmorLock(double armor, String... requirements) {
            this.armor = armor;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkDouble(armor) & CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new ArmorLockKey(armor), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Armor Lock of: " + armor + ", With Requirements: " + descString;
        }
    }
}