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
import net.minecraft.item.ItemStack;
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
        ItemStack s;
        String[] r;

        Add(IItemStack stack, String... requirements) {
            if (CheckMethods.checkIItemstack(stack) && CheckMethods.checkStringArray(requirements)) {
                this.s = CraftTweakerMC.getItemStack(stack);
                this.r = requirements;
            }
        }

        @Override
        public void apply() {
            RequirementHolder h = RequirementHolder.fromStringList(r);
            LevelLockHandler.addLock(s, h);
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String s : r) {
                descString.append(s).append(", ");
            }
            return "Setting the requirement of: " + s.getDisplayName() + " to: " + descString;
        }
    }
}
