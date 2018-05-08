package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.network.MessageLockedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.List;

public class DimensionLockHandler {
    @SubscribeEvent
    public void onDimensionChanged(PlayerEvent.PlayerChangedDimensionEvent event) {
        //Check their armor to make sure they still can use it in that dimension
        EntityPlayer player = event.player;
        for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
            ItemStack stack = player.inventory.armorInventory.get(i);
            if (!LevelLockHandler.canPlayerUseItem(player, stack)) {
                ItemStack copy = stack.copy();
                if (!player.inventory.addItemStackToInventory(copy)) {
                    player.dropItem(copy, false);
                }
                player.inventory.armorInventory.set(i, ItemStack.EMPTY);
                LevelLockHandler.tellPlayer(player, stack, MessageLockedItem.MSG_ARMOR_EQUIP_LOCKED);
            }
        }
    }

    @SubscribeEvent
    public void onDimensionChanging(EntityTravelToDimensionEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntity();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new DimensionLockKey(event.getDimension()));
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.dimension.travelError");
            TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
            List<Requirement> requirements = requirementHolder.getRequirements();
            StringBuilder reqString = new StringBuilder();
            for (Requirement requirement : requirements) {
                reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
            }
            ITextComponent textComponent = new TextComponentString(error + " " + error2 + " " + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}
