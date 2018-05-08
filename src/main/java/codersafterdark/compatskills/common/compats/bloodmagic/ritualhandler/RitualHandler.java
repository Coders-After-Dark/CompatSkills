package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.event.RitualEvent.RitualActivatedEvent;
import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class RitualHandler {
    @SubscribeEvent
    public void ritualEvent(RitualActivatedEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLocks(Ritual.class, event.getRitual());
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            List<Requirement> requirements = requirementHolder.getRequirements();
            TextComponentTranslation error = new TextComponentTranslation("compatskills.bloodmagic.ritualError");
            StringBuilder reqs = new StringBuilder("\n" + "With Requirements: ");
            for (Requirement req : requirements) {
                reqs.append("\n").append(req.getToolTip(data));
            }
            player.sendStatusMessage(new TextComponentString(error + reqs.toString()), false);
        }
    }
}