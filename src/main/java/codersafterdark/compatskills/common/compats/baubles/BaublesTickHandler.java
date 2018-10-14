package codersafterdark.compatskills.common.compats.baubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.network.MessageLockedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class BaublesTickHandler {
    @SubscribeEvent
    public void tickHandler(PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (!ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }
        IBaublesItemHandler baublesHandler = BaublesApi.getBaublesHandler(player);
        for (int i = 0; i < baublesHandler.getSlots(); i++) {
            ItemStack stack = baublesHandler.getStackInSlot(i);
            if (!LevelLockHandler.canPlayerUseItem(player, stack)) {
                if (!player.inventory.addItemStackToInventory(stack)) {
                    player.dropItem(stack, false);
                }
                baublesHandler.setStackInSlot(i, ItemStack.EMPTY);
                LevelLockHandler.tellPlayer(player, stack, MessageLockedItem.MSG_ARMOR_EQUIP_LOCKED);
            }
        }
    }
}