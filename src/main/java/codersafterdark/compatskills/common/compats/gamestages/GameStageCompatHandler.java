package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.compatskills.common.compats.gamestages.gamestagelocks.GameStageLockHandler;
import codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement.GameStageRequirement;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import net.minecraftforge.common.MinecraftForge;

public class GameStageCompatHandler {
    private static GameStageLockHandler lockHandler;

    public static void setup() {
        lockHandler = new GameStageLockHandler();
        MinecraftForge.EVENT_BUS.register(lockHandler);
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("stage", GameStageRequirement::new);
        registry.addRequirementHandler("!stage", input -> registry.getRequirement("not|stage|" + input));
    }
}
