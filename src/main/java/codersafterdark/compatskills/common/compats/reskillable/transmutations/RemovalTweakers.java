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
@ZenClass("mods.compatskills.transmutations.removals")
@ZenRegister
public class RemovalTweakers {

    // removeStartStateFromReagent
    @ZenMethod
    public static void removeStartStateFromReagent(IItemStack reagent, IItemStack startState) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeStartStateFromReagent(reagent, startState));
        }
    }

    @ZenMethod
    public static void removeStartStateFromReagent(IItemStack reagent, crafttweaker.api.block.IBlockState state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeStartStateFromReagent(reagent, state));
        }
    }

    // removeStartStateReagentAgnostic
    @ZenMethod
    public static void removeStartStateReagentAgnostic(IItemStack state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeStartStateReagentAgnostic(state));
        }
    }

    @ZenMethod
    public static void removeStartStateReagentAgnostic(crafttweaker.api.block.IBlockState state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeStartStateReagentAgnostic(state));
        }
    }

    // removeEndStateFromReagent
    @ZenMethod
    public static void removeEndStateFromReagent(IItemStack reagent, IItemStack state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeEndStateFromReagent(reagent, state));
        }
    }

    @ZenMethod
    public static void removeEndStateFromReagent(IItemStack reagent, crafttweaker.api.block.IBlockState state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeEndStateFromReagent(reagent, state));
        }
    }

    // removeEndStateReagentAgnostic
    @ZenMethod
    public static void removeEndStateReagentAgnostic(IItemStack state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeEndStateReagentAgnostic(state));
        }
    }

    @ZenMethod
    public static void removeEndStateReagentAgnostic(crafttweaker.api.block.IBlockState state) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new removeEndStateReagentAgnostic(state));
        }
    }

    private static class removeStartStateFromReagent implements IAction {
        Item reagent;
        IBlockState state;

        removeStartStateFromReagent(IItemStack reagent, IItemStack state) {
            this.reagent = CraftTweakerMC.getItemStack(reagent).getItem();
            this.state = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state));
        }

        removeStartStateFromReagent(IItemStack reagent, crafttweaker.api.block.IBlockState state) {
            this.reagent = CraftTweakerMC.getItemStack(reagent).getItem();
            this.state = CraftTweakerMC.getBlockState(state);
        }

        @Override
        public void apply() {
            TransmutationRegistry.removeStartStateFromReagent(reagent, state);
        }

        @Override
        public String describe() {
            return "Removing Start State: " + state.getBlock().getLocalizedName() + " From Reagent: " + reagent.getItemStackDisplayName(new ItemStack(reagent));
        }
    }

    private static class removeStartStateReagentAgnostic implements IAction {
        IBlockState state;

        removeStartStateReagentAgnostic(IItemStack state) {
            this.state = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state));
        }

        removeStartStateReagentAgnostic(crafttweaker.api.block.IBlockState state) {
            this.state = CraftTweakerMC.getBlockState(state);
        }

        @Override
        public void apply() {
            TransmutationRegistry.removeStartStateFromReagentAgnostic(state);
        }

        @Override
        public String describe() {
            return "Removing Start State: " + state.getBlock().getLocalizedName() + " From All Reagents!";
        }
    }

    private static class removeEndStateFromReagent implements IAction {
        Item reagent;
        IBlockState state;

        removeEndStateFromReagent(IItemStack reagent, IItemStack state) {
            this.reagent = CraftTweakerMC.getItemStack(reagent).getItem();
            this.state = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state));
        }

        removeEndStateFromReagent(IItemStack reagent, crafttweaker.api.block.IBlockState state) {
            this.reagent = CraftTweakerMC.getItemStack(reagent).getItem();
            this.state = CraftTweakerMC.getBlockState(state);
        }

        @Override
        public void apply() {
            TransmutationRegistry.removeEndStateFromReagent(reagent, state);
        }

        @Override
        public String describe() {
            return "Removing End State: " + state.getBlock().getLocalizedName() + " From Reagent: " + reagent.getItemStackDisplayName(new ItemStack(reagent));
        }
    }

    private static class removeEndStateReagentAgnostic implements IAction {
        IBlockState state;

        removeEndStateReagentAgnostic(IItemStack state) {
            this.state = CheckMethods.convertItemStackToIBlockState(CraftTweakerMC.getItemStack(state));
        }

        removeEndStateReagentAgnostic(crafttweaker.api.block.IBlockState state) {
            this.state = CraftTweakerMC.getBlockState(state);
        }

        @Override
        public void apply() {
            TransmutationRegistry.removeEndStateFromReagentAgnostic(state);
        }

        @Override
        public String describe() {
            return "Removing End State: " + state.getBlock().getLocalizedName() + " From All Reagents!";
        }
    }
}
