package codersafterdark.compatskills.common.compats.minecraft.item.weapon;

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

@ZenClass("mods.compatskills.DamageLock")
@ZenRegister
public class WeaponLockTweaker {
    @ZenMethod
    public static void addDamageLock(double damage, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddDamageLock(damage, requirements));
    }

    private static class AddDamageLock implements IAction {
        private final double damage;
        private final String[] requirements;

        private AddDamageLock(double damage, String... requirements) {
            this.damage = damage;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkDouble(damage) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new AttackDamageLockKey(damage), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Attack Damage Lock of: " + damage + ", With Requirements: " + descString;
        }
    }
}