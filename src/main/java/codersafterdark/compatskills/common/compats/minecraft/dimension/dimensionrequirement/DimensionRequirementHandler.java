package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement;

import codersafterdark.reskillable.api.requirement.RequirementCache;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.network.MessageLockedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class DimensionRequirementHandler {
    @SubscribeEvent
    public void onDimensionChanged(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        RequirementCache.invalidateCache(player, DimensionRequirement.class); //Invalidate the cache before checking
        //Check their armor to make sure they still can use it in that dimension
        for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
            ItemStack stack = player.inventory.armorInventory.get(i);
            if (!LevelLockHandler.canPlayerUseItem(player, stack)) {
                if (!player.inventory.addItemStackToInventory(stack)) {
                    player.dropItem(stack, false);
                }
                player.inventory.armorInventory.set(i, ItemStack.EMPTY);
                LevelLockHandler.tellPlayer(player, stack, MessageLockedItem.MSG_ARMOR_EQUIP_LOCKED);
            }
        }
    }
}