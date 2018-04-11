package codersafterdark.compatskills.common.compats.bloodmagic.BindHandler;

import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.BindHandler")
@ZenRegister
public class BindHandlerTweaker {

    @ZenMethod
    public static void addBindLock(String failureMessage, IItemStack stack, String... requirements) {
        BindHandler handler = new BindHandler();
        if (checkMessage(failureMessage) && checkStack(stack) && checkRequirements(requirements)) {
            ItemStack stack2 = CraftTweakerMC.getItemStack(stack);
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            handler.setFailureMessage(failureMessage);
            handler.addBindHolder(stack2, holder);
        }
    }

    private static boolean checkMessage(String message) {
        if (message == null || message.isEmpty()) {
            CraftTweakerAPI.logError("'Failure Message' Param is either null or empty!");
            return false;
        }
        return true;
    }

    private static boolean checkStack(IItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            CraftTweakerAPI.logError("'Stack' Param is either Null or Empty!");
            return false;
        }
        return true;
    }

    private static boolean checkRequirements(String[] reqs) {
        if (reqs == null || reqs.length == 0) {
            CraftTweakerAPI.logError("No Requirements Specified");
            return false;
        }
        for (String req : reqs) {
            if (req == null || req.isEmpty()) {
                CraftTweakerAPI.logError("Requirement String was found to either be Null or Empty!");
                return false;
            }
        }
        return true;
    }
}
