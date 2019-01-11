package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.cache.CapabilityProviderQuestCache;
import betterquesting.api2.cache.QuestCache;
import betterquesting.api2.storage.DBEntry;
import codersafterdark.reskillable.api.event.CacheInvalidatedEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BQTaskHandler {
    @SubscribeEvent
    public void onCacheInvalidate(CacheInvalidatedEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.world.isRemote) {
            QuestCache qc = player.getCapability(CapabilityProviderQuestCache.CAP_QUEST_CACHE, null);
            if (qc == null) {
                return;
            }

            for (DBEntry<IQuest> entry : QuestingAPI.getAPI(ApiReference.QUEST_DB).bulkLookup(qc.getActiveQuests())) {
                for (DBEntry<ITask> task : entry.getValue().getTasks().getEntries()) {
                    if (task.getValue() instanceof TaskRequirement) {
                        task.getValue().detect(player, entry.getValue());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityLiving(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entityLiving = event.getEntityLiving();
        if (entityLiving instanceof EntityPlayer && !entityLiving.world.isRemote && entityLiving.ticksExisted % 100 == 0 && !QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE)) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            QuestCache qc = player.getCapability(CapabilityProviderQuestCache.CAP_QUEST_CACHE, null);
            if (qc == null) {
                return;
            }

            for (DBEntry<IQuest> entry : QuestingAPI.getAPI(ApiReference.QUEST_DB).bulkLookup(qc.getActiveQuests())) {
                for (DBEntry<ITask> task : entry.getValue().getTasks().getEntries()) {
                    if (task.getValue() instanceof TaskRequirement && ((TaskRequirement) task.getValue()).hasUncacheable()) {
                        task.getValue().detect(player, entry.getValue());
                    }
                }
            }
        }
    }
}