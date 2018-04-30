package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.compatskills.utils.CheckMethods;
import crafttweaker.CraftTweakerAPI;
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
        if (CheckMethods.checkString(gamestage) & CheckMethods.checkStringArray(defaultRequirements)) {
            CraftTweakerAPI.apply(new ActionAddTrueGameStageLock(gamestage, defaultRequirements));
        }
    }
}
