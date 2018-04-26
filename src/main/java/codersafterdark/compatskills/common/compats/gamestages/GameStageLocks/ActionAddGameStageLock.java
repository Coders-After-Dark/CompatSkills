package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.compatskills.utils.CheckMethods;
import crafttweaker.IAction;

import java.util.Arrays;

public abstract class ActionAddGameStageLock implements IAction {
    private final String gamestage;
    private final String[] defaultRequirements;

    public ActionAddGameStageLock(String gamestage, String[] defaultRequirements){
        this.gamestage = gamestage;
        this.defaultRequirements = defaultRequirements;
    }

    @Override
    public void apply() {
        if (CheckMethods.checkString(gamestage) & CheckMethods.checkStringArray(defaultRequirements)){
            addToHandler(new GameStageLock(gamestage, defaultRequirements));
        }
    }

    public abstract void addToHandler(GameStageLock lock);

    @Override
    public String describe() {
        return "Added GameStage Lock" + this.gamestage + ", With Requirements: " + Arrays.toString(this.defaultRequirements) ;
    }

    public String getGamestage() {
        return gamestage;
    }

    public String[] getDefaultRequirements() {
        return defaultRequirements;
    }
}
