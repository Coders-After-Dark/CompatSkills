package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;
import codersafterdark.reskillable.api.event.CacheInvalidatedEvent;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BQTaskHandler {
    @SubscribeEvent
    public void onCacheInvalidate(CacheInvalidatedEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.world.isRemote) {
            ParticipantInfo pInfo = new ParticipantInfo(player);
            List<DBEntry<IQuest>> activeQuests = QuestingAPI.getAPI(ApiReference.QUEST_DB).bulkLookup(pInfo.getSharedQuests());
            for (DBEntry<IQuest> entry : activeQuests) {
                for (DBEntry<ITask> task : entry.getValue().getTasks().getEntries()) {
                    if (task.getValue() instanceof TaskRequirement) {
                        task.getValue().detect(pInfo, entry);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityLiving(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entityLiving = event.getEntityLiving();
        if (entityLiving instanceof EntityPlayer && !entityLiving.world.isRemote && entityLiving.ticksExisted % 100 == 0 &&
            !QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE)) {
            ParticipantInfo pInfo = new ParticipantInfo((EntityPlayer) entityLiving);
            List<DBEntry<IQuest>> activeQuests = QuestingAPI.getAPI(ApiReference.QUEST_DB).bulkLookup(pInfo.getSharedQuests());
            for (DBEntry<IQuest> entry : activeQuests) {
                for (DBEntry<ITask> task : entry.getValue().getTasks().getEntries()) {
                    if (task.getValue() instanceof TaskRequirement && ((TaskRequirement) task.getValue()).hasUncacheable()) {
                        task.getValue().detect(pInfo, entry);
                    }
                }
            }
        }
    }
}