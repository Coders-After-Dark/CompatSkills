package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.ModLock")
@ZenRegister
public class ModLockTweaker {

    @ZenMethod
    public static void addModLock(String modId, String... locked) {
        StringBuilder descString = new StringBuilder("Requirements: ");

        if (CheckMethods.checkStringArray(locked)) {
            for (String string : locked) {
                descString.append(string).append(", ");
            }
        }

        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                if (CheckMethods.checkString(modId) && CheckMethods.checkModLoaded(modId) && CheckMethods.checkStringArray(locked)) {
                    RequirementHolder holder = RequirementHolder.fromStringList(locked);
                    LevelLockHandler.addModLock(modId, holder);
                }
            }

            @Override
            public String describe() {
                return "Setting the requirement of Mod: " + modId + " to: " + descString;
            }
        });
    }
}
