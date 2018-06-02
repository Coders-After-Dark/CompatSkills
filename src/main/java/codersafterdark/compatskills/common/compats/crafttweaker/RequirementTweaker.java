package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.Requirement")
@ZenRegister
public class RequirementTweaker {
    @ZenMethod
    public static void addRequirement(IItemStack item, String... locked) {
        CompatSkills.LATE_ADDITIONS.add(new Add(item, locked));
    }

    private static class Add implements IAction {
        private final IItemStack stack;
        private final String[] requirements;

        private Add(IItemStack stack, String... requirements) {
            this.stack = stack;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkIItemstack(stack) & CheckMethods.checkStringArray(requirements)) {
                RequirementHolder h = RequirementHolder.fromStringList(requirements);
                LevelLockHandler.addLock(CraftTweakerMC.getItemStack(stack), h);
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String s : requirements) {
                descString.append(s).append(", ");
            }
            return "Setting the requirement of: " + (stack == null ? "null" : stack.getDisplayName()) + " to: " + descString;
        }
    }
}