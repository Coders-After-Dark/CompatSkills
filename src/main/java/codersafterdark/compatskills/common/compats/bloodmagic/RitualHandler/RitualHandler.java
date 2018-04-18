package codersafterdark.compatskills.common.compats.bloodmagic.RitualHandler;

import WayofTime.bloodmagic.event.RitualEvent.RitualActivatedEvent;
import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class RitualHandler {

    private String failureMessage;

    private Map<Ritual, RequirementHolder> ritualHolder;

    public RitualHandler() {
        ritualHolder = Maps.newHashMap();
    }

    public void addRitualHolder(Ritual ritual, RequirementHolder holder) {
        ritualHolder.put(ritual, holder);
    }

    @SubscribeEvent
    public void ritualEvent(RitualActivatedEvent event) {
        Ritual ritual = event.getRitual();
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        if (ritualHolder.containsKey(ritual)) {
            if (!data.matchStats(ritualHolder.get(ritual))) {
                event.setCanceled(true);
                if (player.getEntityWorld().isRemote) {
                    player.sendStatusMessage(new TextComponentString(failureMessage), true);
                }
            }
        }
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
