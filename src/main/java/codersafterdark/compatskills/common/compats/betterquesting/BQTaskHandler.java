package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.api.QuestingAPI;
import betterquesting.api.questing.IQuest;
import betterquesting.api.utils.QuestCache;
import codersafterdark.reskillable.api.event.CacheInvalidatedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class BQTaskHandler {
    @SubscribeEvent
    public void onCacheInvalidate(CacheInvalidatedEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.world.isRemote) {
            for (Map.Entry<TaskRequirement, IQuest> entry : QuestCache.INSTANCE.getActiveTasks(QuestingAPI.getQuestingUUID(player), TaskRequirement.class).entrySet()) {
                entry.getKey().detect(player, entry.getValue());
            }
        }
    }
}