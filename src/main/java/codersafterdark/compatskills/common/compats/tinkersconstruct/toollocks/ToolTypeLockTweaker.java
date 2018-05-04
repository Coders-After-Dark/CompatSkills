package codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("tconstruct")
@ZenClass("mods.compatskills.ToolTypeLock")
@ZenRegister
public class ToolTypeLockTweaker {

    @ZenMethod
    public static void addToolTypeLock(IItemStack stack, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddToolTypeLock(stack, requirements));
    }

    private static class AddToolTypeLock implements IAction {
        IItemStack stack;
        String[] requirements;

        AddToolTypeLock(IItemStack stack, String... requirements){
            this.stack = stack;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkIItemstack(stack) && CheckMethods.checkStringArray(requirements)){
                LevelLockHandler.addLockByKey(new ToolTypeLockKey(CraftTweakerMC.getItemStack(stack).getItem()), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder(" With Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Tool-Type Lock for Tool-Type: " + stack.getDisplayName() + descString;
        }
    }
}
