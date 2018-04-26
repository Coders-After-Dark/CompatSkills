package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.compatskills.common.compats.gamestages.GameStageCompatHandler;

public class ActionAddTrueGameStageLock extends ActionAddGameStageLock {

    public ActionAddTrueGameStageLock (String gamestage, String... defaultRequirements){
        super(gamestage, defaultRequirements);
    }

    @Override
    public void addToHandler(GameStageLock lock) {
        GameStageCompatHandler.addGameStageLock(lock);
    }

}
