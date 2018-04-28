package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
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
        StringBuilder descString = new StringBuilder("Requirements: ");

        if (CheckMethods.checkStringArray(locked)) {
            for (String string : locked) {
                descString.append(string).append(", ");
            }
        }

        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                if (CheckMethods.checkIItemstack(item) & CheckMethods.checkStringArray(locked)) {
                    ItemStack i = CraftTweakerMC.getItemStack(item);
                    RequirementHolder h = RequirementHolder.fromStringList(locked);
                    LevelLockHandler.addLock(i, h);
                }
            }

            @Override
            public String describe() {
                return "Setting the requirement of: " + item + " to: " + descString;
            }
        });
    }
}
