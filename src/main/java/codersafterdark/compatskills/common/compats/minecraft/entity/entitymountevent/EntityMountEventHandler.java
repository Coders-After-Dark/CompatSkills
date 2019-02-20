package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityMountEventHandler {
    @SubscribeEvent
    public void onMount(EntityMountEvent event) {
        if (event.isCanceled() || event.isDismounting() || !(event.getEntityMounting() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntityMounting();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new EntityMountKey(event.getEntityBeingMounted()));
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.entity.mount");
            player.sendStatusMessage(Utils.getError(requirementHolder, data, error), false);
        }
    }
}