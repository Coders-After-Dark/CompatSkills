package codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement;

import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GameStageRequirementHandler {
    @SubscribeEvent
    public void gameStageAdded(GameStageEvent.Added event) {
        RequirementCache.invalidateCache(event.getEntityPlayer(), GameStageRequirement.class);
    }

    @SubscribeEvent
    public void gameStageRemoved(GameStageEvent.Removed event) {
        RequirementCache.invalidateCache(event.getEntityPlayer(), GameStageRequirement.class);
    }
}