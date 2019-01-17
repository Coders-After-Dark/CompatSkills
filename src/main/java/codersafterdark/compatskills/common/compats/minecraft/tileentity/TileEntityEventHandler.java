package codersafterdark.compatskills.common.compats.minecraft.tileentity;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TileEntityEventHandler {
    @SubscribeEvent
    public void onTileEntityInteract(RightClickBlock event) {
        checkEvent(event);
    }

    @SubscribeEvent
    public void onTileEntityInteract(LeftClickBlock event) {
        checkEvent(event);
    }

    private void checkEvent(PlayerInteractEvent event) {
        if (event.isCanceled()) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        if (!ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }
        TileEntity entity = event.getWorld().getTileEntity(event.getPos());
        if (entity != null) {
            ResourceLocation location = TileEntity.getKey(entity.getClass());
            if (location != null) {
                PlayerData data = PlayerDataHandler.get(player);
                RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new TileEntityLockKey(location.toString()));
                if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
                    event.setCanceled(true);
                    if (player.getEntityWorld().isRemote) {
                        TextComponentTranslation error = new TextComponentTranslation("compatskills.tileentity.interactError");
                        player.sendStatusMessage(Utils.getError(requirementHolder, data, error), false);
                    }
                }
            }
        }
    }
}