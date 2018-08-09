package codersafterdark.compatskills.common.compats.gamestages.gamestagelocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.gamestages.GameStageCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
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
        if (GameStageCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddGameStageLock(gamestage, defaultRequirements));
        }
    }

    private static class AddGameStageLock implements IAction {
        private final String gameStage;
        private final String[] requirements;

        private AddGameStageLock(String gameStage, String... requirements) {
            this.gameStage = gameStage;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkString(gameStage) & CheckMethods.checkStringArray(requirements)) {
                GameStageCompatHandler.addGameStageLock(new GameStageLock(gameStage), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added GameStage Lock: " + gameStage + ", With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}