package codersafterdark.compatskills.common.compats.reskillable.transmutations;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.transmutations.TransmutationRegistry;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("reskillable")
@ZenClass("mods.compatskills.transmutations.additions")
@ZenRegister
public class AdditionsTweaker {
    /// addEntryToReagent
    @ZenMethod
    public static void addEntryToReagent(IItemStack reagent, IItemStack startState, IItemStack endState){
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddEntryToReagent(CraftTweakerMC.getItemStack(reagent).getItem(), startState, endState));
        }
    }

    @ZenMethod
    public static void addEntryToReagent(IItemStack reagent, crafttweaker.api.block.IBlockState state1, crafttweaker.api.block.IBlockState state2) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddEntryToReagent(CraftTweakerMC.getItemStack(reagent).getItem(), CraftTweakerMC.getBlockState(state1), CraftTweakerMC.getBlockState(state2)));
        }
    }

    /// addEntryToReagentAgnostic
    @ZenMethod
    public static void addEntryToReagentAgnostic(IItemStack startState, IItemStack endState) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddEntryToAgnosticReagent(startState, endState));
        }
    }

    @ZenMethod
    public static void addEntryToReagentAgnostic(crafttweaker.api.block.IBlockState state1, crafttweaker.api.block.IBlockState state2) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddEntryToAgnosticReagent(CraftTweakerMC.getBlockState(state1), CraftTweakerMC.getBlockState(state2)));
        }
    }

    private static class AddEntryToReagent implements IAction {
        private final Item reagent;
        private final IBlockState state1;
        private final IBlockState state2;

        private AddEntryToReagent(Item reagent, IBlockState state1, IBlockState state2) {
            this.reagent = reagent;
            this.state1 = state1;
            this.state2 = state2;
        }

        private AddEntryToReagent(Item reagent, IItemStack state1, IItemStack state2) {
            this.reagent = reagent;
            this.state1 = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state1));
            this.state2 = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state2));
        }

        @Override
        public void apply() {
            TransmutationRegistry.addEntryToReagent(reagent, state1, state2);
        }

        @Override
        public String describe() {
            return "Added Transmutation for " + state1.getBlock().getLocalizedName() + " -> " + state2.getBlock().getLocalizedName() + " :: Using Reagent: " + new ItemStack(reagent).getDisplayName();
        }
    }

    private static class AddEntryToAgnosticReagent implements IAction {
        private final IBlockState state1;
        private final IBlockState state2;

        private AddEntryToAgnosticReagent(IBlockState state1, IBlockState state2) {
            this.state1 = state1;
            this.state2 = state2;
        }

        private AddEntryToAgnosticReagent(IItemStack state1, IItemStack state2) {
            this.state1 = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state1));
            this.state2 = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state2));
        }

        @Override
        public void apply() {
            TransmutationRegistry.addEntryReagentAgnostic(state1, state2);
        }

        @Override
        public String describe() {
            return "Added Transmutation for " + state1.getBlock().getLocalizedName() + " -> " + state2.getBlock().getLocalizedName() + " :: Using All Reagents";
        }
    }
}