package codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.tinkersconstruct.TinkersCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ModOnly("tconstruct")
@ZenClass("mods.compatskills.ToolTypeLock")
@ZenRegister
public class ToolTypeLockTweaker {
    @ZenMethod
    public static void addToolTypeLock(IItemStack stack, String... requirements) {
        if (TinkersCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddToolTypeLock(stack, requirements));
        }
    }

    private static class AddToolTypeLock implements IAction {
        private final IItemStack stack;
        private final String[] requirements;

        private AddToolTypeLock(IItemStack stack, String... requirements) {
            this.stack = stack;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkIItemstack(stack) & CheckMethods.checkStringArray(requirements)) {
                TinkersCompatHandler.addTinkersLock(new ToolTypeLockKey(CraftTweakerMC.getItemStack(stack).getItem()), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Tool-Type Lock for Tool-Type: " + (stack == null ? "null" : stack.getDisplayName()) + " With Requirements: " + descString;
        }
    }
}