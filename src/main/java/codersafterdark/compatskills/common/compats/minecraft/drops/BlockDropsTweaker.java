package codersafterdark.compatskills.common.compats.minecraft.drops;

import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.BlockDropsLock")
public class BlockDropsTweaker {
    @ZenMethod
    public static void addBlockDropLock(IItemStack stack, String... requirements) {
        if (MinecraftCompatHandler.ENABLED) {

        }
    }

    private static class AddBlockDropLock implements IAction {
        private final IItemStack stack;
        private final String[] requirements;

        private AddBlockDropLock(IItemStack stack, String... requirements) {
            this.stack = stack;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkIItemstack(stack) && CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new ItemStackDropKey(CraftTweakerMC.getItemStack(stack)), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Block Drop Lock for ItemStack: " + stack.getDisplayName() + ", With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}
