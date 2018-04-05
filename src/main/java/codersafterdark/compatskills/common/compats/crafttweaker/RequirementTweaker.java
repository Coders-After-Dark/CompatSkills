package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.Requirement")
@ZenRegister
public class RequirementTweaker {

    @ZenMethod
    public static void addRequirement(IItemStack item, String locked) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                if (item == null) {
                    CraftTweakerAPI.logError("Itemstack: " + item + " was found to be either null or empty!");
                    return;
                } else if (locked == null || locked.isEmpty()) {
                    CraftTweakerAPI.logError("String: " + locked + " was found to be either null or empty!");
                    return;
                } else {
                    ItemStack i = CraftTweakerMC.getItemStack(item);
                    RequirementHolder h = RequirementHolder.fromString(locked);
                    LevelLockHandler.craftTweakerLocks.put(i, h);
                }
            }

            @Override
            public String describe() {
                return "Setting the requirement of: " + item + " to: " + locked;
            }
        });

    }
}
