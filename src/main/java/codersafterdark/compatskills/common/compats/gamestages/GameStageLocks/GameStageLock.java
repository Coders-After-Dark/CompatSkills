package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.reskillable.api.data.RequirementHolder;

public class GameStageLock {
    private final String gamestage;
    private final RequirementHolder holder;

    public GameStageLock(String gamestage, String... defaultRequirements){
        this.gamestage = gamestage;
        this.holder = RequirementHolder.fromStringList(defaultRequirements);
    }

    public String getGamestage() {
        return gamestage;
    }

    public RequirementHolder getHolder() {
        return holder;
    }
}
