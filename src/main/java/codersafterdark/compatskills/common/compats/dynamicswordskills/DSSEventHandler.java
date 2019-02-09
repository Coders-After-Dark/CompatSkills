package codersafterdark.compatskills.common.compats.dynamicswordskills;

import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DSSEventHandler {
    @SubscribeEvent
    public void interactEvent(PlayerInteractEvent.RightClickItem event) {
        RequirementCache.invalidateCache(event.getEntityPlayer(), DSSkillRequirement.class);
    }

    @SubscribeEvent
    public void interactEvent(PlayerInteractEvent.RightClickBlock event) {
        RequirementCache.invalidateCache(event.getEntityPlayer(), DSSkillRequirement.class);
    }
}