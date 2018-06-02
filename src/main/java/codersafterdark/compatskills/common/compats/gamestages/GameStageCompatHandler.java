package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.compatskills.common.compats.gamestages.gamestagelocks.GameStageLockHandler;
import codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement.GameStageRequirement;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import net.minecraftforge.common.MinecraftForge;

public class GameStageCompatHandler {
    public static void setup() {
        MinecraftForge.EVENT_BUS.register(new GameStageLockHandler());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("stage", GameStageRequirement::new);
        registry.addRequirementHandler("!stage", input -> registry.getRequirement("not|stage|" + input));
    }
}