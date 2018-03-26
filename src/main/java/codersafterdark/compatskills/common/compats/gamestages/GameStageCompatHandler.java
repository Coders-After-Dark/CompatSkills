package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.ReskillableAPI;

public class GameStageCompatHandler {

    public static void setup(){
        ReskillableAPI.getInstance().getRequirementRegistry().addRequirementHandler("stage", input -> new GameStageRequirement(input));
    }
}
