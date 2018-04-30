package codersafterdark.compatskills.common.compats.bloodmagic.BindHandler;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.bloodmagic.BMCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("bloodmagic")
@ZenClass("mods.compatskills.BindHandler")
@ZenRegister
public class BindHandlerTweaker {

    @ZenMethod
    public static void addBindLock(String failureMessage, IItemStack stack, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new Add(failureMessage, stack, requirements));
    }

    private static class Add implements IAction {
        String failureMessage;
        ItemStack stack;
        RequirementHolder holder;
        String[] requirements;


        Add(String failureMessage, IItemStack stack, String... requirements){
            if (CheckMethods.checkString(failureMessage)){
                this.failureMessage = failureMessage;
            }
            if (CheckMethods.checkIItemstack(stack)) {
                this.stack = CraftTweakerMC.getItemStack(stack);
            }
            if (CheckMethods.checkStringArray(requirements)) {
                this.holder = RequirementHolder.fromStringList(requirements);
                this.requirements = requirements;
            }
        }

        @Override
        public void apply() {
            BMCompatHandler.addBindLock(failureMessage, stack, holder);
        }

        @Override
        public String describe() {
            StringBuilder reqString = new StringBuilder("Requirements: ");
            for (String string : requirements){
                reqString.append(string).append(", ");
            }
            return "Adding Binding Lock for ItemStack: " + stack.getDisplayName() + " With " + reqString;
        }
    }
}
