package codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnimalTameEventHandler {
    @SubscribeEvent
    public void onTame(AnimalTameEvent event) {
        if (event.isCanceled()) {
            return;
        }
        EntityPlayer player = event.getTamer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new EntityTameKey(event.getAnimal()));
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.entity.tame");
            player.sendStatusMessage(Utils.getError(requirementHolder, data, error), false);
        }
    }
}