package codersafterdark.compatskills.common.compats.baubles;

import baubles.api.BaublesApi;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.network.MessageLockedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BaublesTickHandler {
    @SubscribeEvent
    public void tickHandler(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (!player.isCreative() && !LevelLockHandler.isFake(player)){
            for (int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); i++) {
                ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(i);
                if (!stack.isEmpty() && !LevelLockHandler.canPlayerUseItem(player, stack)) {
                    event.player.dropItem(stack, false);
                    BaublesApi.getBaublesHandler(player).setStackInSlot(i, ItemStack.EMPTY);
                    LevelLockHandler.tellPlayer(player, stack, MessageLockedItem.MSG_ARMOR_EQUIP_LOCKED);
                }
            }
        }
    }
}