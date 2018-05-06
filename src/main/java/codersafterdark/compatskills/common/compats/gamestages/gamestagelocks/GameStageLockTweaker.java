package codersafterdark.compatskills.common.compats.gamestages.gamestagelocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
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
        CompatSkills.LATE_ADDITIONS.add(new AddGameStageLock(gamestage, defaultRequirements));
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
                RequirementHolder holder = RequirementHolder.fromStringList(requirements);
                LevelLockHandler.addLockByKey(new GameStageLock(gameStage), holder);
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added GameStage Lock: " + gameStage + ", With Requirements: " + descString;
        }
    }
}