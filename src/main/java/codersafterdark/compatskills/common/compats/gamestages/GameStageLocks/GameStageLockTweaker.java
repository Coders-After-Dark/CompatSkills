package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("gamestages")
@ZenClass("mods.compatskills.GameStageLocks")
@ZenRegister
public class GameStageLockTweaker {
    @ZenMethod
    public static void addGameStageLock(String gamestage, String... defaultRequirements) {
        if (CheckMethods.checkString(gamestage) && CheckMethods.checkStringArray(defaultRequirements)) {
            StringBuilder descString = new StringBuilder("Requirements: ");

            for (String string : defaultRequirements) {
                descString.append(string).append(", ");
            }

            CraftTweakerAPI.apply(new IAction() {
                @Override
                public void apply() {
                    RequirementHolder holder = RequirementHolder.fromStringList(defaultRequirements);
                    LevelLockHandler.addLockByKey(new GameStageLock(gamestage), holder);
                }

                @Override
                public String describe() {
                    return "Added GameStage Lock: " + gamestage + ", With Requirements: " + descString;
                }
            });
        }
    }
}
