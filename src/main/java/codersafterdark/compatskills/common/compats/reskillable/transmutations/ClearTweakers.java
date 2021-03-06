package codersafterdark.compatskills.common.compats.reskillable.transmutations;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.reskillable.api.transmutations.TransmutationRegistry;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("reskillable")
@ZenClass("mods.compatskills.transmutations.clears")
@ZenRegister
public class ClearTweakers {
    @ZenMethod
    public static void clearMapOfReagent(IItemStack stack) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new ClearMapOfReagent(stack));
        }
    }

    @ZenMethod
    public static void clearReagentOfEntries(IItemStack stack) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new ClearReagentOfEntries(stack));
        }
    }

    @ZenMethod
    public static void clearReagentMap() {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new ClearReagentMap());
        }
    }

    private static class ClearMapOfReagent implements IAction {
        private final Item reagent;

        private ClearMapOfReagent(IItemStack stack) {
            this.reagent = CraftTweakerMC.getItemStack(stack).getItem();
        }

        @Override
        public void apply() {
            TransmutationRegistry.clearMapOfReagent(reagent);
        }

        @Override
        public String describe() {
            return "Clearing Map of Reagent: " + reagent.getItemStackDisplayName(new ItemStack(reagent));
        }
    }

    private static class ClearReagentOfEntries implements IAction {
        private final Item reagent;

        private ClearReagentOfEntries(IItemStack stack) {
            this.reagent = CraftTweakerMC.getItemStack(stack).getItem();
        }

        @Override
        public void apply() {
            TransmutationRegistry.clearReagentOfEntries(reagent);
        }

        @Override
        public String describe() {
            return "Clearing Reagent: " + reagent.getItemStackDisplayName(new ItemStack(reagent)) + " Of Entries!";
        }
    }

    private static class ClearReagentMap implements IAction {
        @Override
        public void apply() {
            TransmutationRegistry.clearReagentMap();
        }

        @Override
        public String describe() {
            return "Clearing Transmutation Map";
        }
    }
}