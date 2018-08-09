package codersafterdark.compatskills.common.compats.minecraft.item.weapon;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.DamageLock")
@ZenRegister
public class DamageLockTweaker {
    @ZenMethod
    public static void addDamageLock(double damage, String... requirements) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddDamageLock(damage, requirements));
        }
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
                MinecraftCompatHandler.addMCLock(new AttackDamageLockKey(damage), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Attack Damage Lock of: " + damage + ", With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}