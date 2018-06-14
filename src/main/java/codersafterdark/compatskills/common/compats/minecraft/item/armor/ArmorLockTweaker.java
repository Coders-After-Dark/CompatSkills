package codersafterdark.compatskills.common.compats.minecraft.item.armor;

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

@ZenClass("mods.compatskills.ArmorLock")
@ZenRegister
public class ArmorLockTweaker {
    @ZenMethod
    public static void addArmorLock(int armor, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddArmorLock(armor, requirements));
    }

    private static class AddArmorLock implements IAction {
        private final int armor;
        private final String[] requirements;

        private AddArmorLock(int armor, String... requirements) {
            this.armor = armor;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkInt(armor) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new ArmorLockKey(armor), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Armor Lock of: " + armor + ", With Requirements: " + descString;
        }
    }
}