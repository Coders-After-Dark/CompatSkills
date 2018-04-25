package codersafterdark.compatskills.common.compats.bloodmagic.BindHandler;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
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
        BindHandler handler = new BindHandler();
        if (CheckMethods.checkString(failureMessage) & CheckMethods.checkIItemstack(stack) & CheckMethods.checkStringArray(requirements)) {
            ItemStack stack2 = CraftTweakerMC.getItemStack(stack);
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            handler.setFailureMessage(failureMessage);
            handler.addBindHolder(stack2, holder);
        }
    }
}
