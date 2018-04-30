package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.event.RitualEvent.RitualActivatedEvent;
import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RitualHandler {
    @SubscribeEvent
    public void ritualEvent(RitualActivatedEvent event) {
        Ritual ritual = event.getRitual();
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        RitualLockKey ritualKey = new RitualLockKey(ritual);
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(ritualKey);
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            if (player.getEntityWorld().isRemote) {
                player.sendStatusMessage(new TextComponentString(MessageStorage.getFailureMessage(ritualKey)), true);
            }
        }
    }
}