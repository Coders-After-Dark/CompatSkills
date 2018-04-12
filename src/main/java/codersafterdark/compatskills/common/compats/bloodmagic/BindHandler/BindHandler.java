package codersafterdark.compatskills.common.compats.bloodmagic.BindHandler;

import WayofTime.bloodmagic.event.ItemBindEvent;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class BindHandler {

    public Map<ItemStack, RequirementHolder> bindHolder;
    private String failureMessage;

    public BindHandler() {
        bindHolder = Maps.newHashMap();
    }

    public void addBindHolder(ItemStack stack, RequirementHolder holder) {
        bindHolder.put(stack, holder);
    }

    @SubscribeEvent
    public void bindEvent(ItemBindEvent event) {
        ItemStack stack = event.getBindingStack();
        EntityPlayer player = event.getNewOwner();
        PlayerData data = PlayerDataHandler.get(player);
        if (bindHolder.containsKey(stack)) {
            if (!data.matchStats(bindHolder.get(stack))) {
                event.setCanceled(true);
                if (player.getEntityWorld().isRemote) {
                    player.sendStatusMessage(new TextComponentString(failureMessage), true);
                }
            }
        }
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}