package codersafterdark.compatskills.common.compats.thaumcraft.requirements;

import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.research.ResearchEvent;

public class TCInvalidateHandler {
    @SubscribeEvent
    public void onResearchEvent(ResearchEvent.Research event) {
        RequirementCache.invalidateCache(event.getPlayer(), ResearchRequirement.class);
    }
}