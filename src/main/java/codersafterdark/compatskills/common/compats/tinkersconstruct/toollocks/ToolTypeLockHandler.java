package codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;

import java.util.List;

public class ToolTypeLockHandler {
    @SubscribeEvent
    public void onToolCrafted(TinkerCraftingEvent.ToolCraftingEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player != null){
            PlayerData data = PlayerDataHandler.get(player);
            Item item = event.getItemStack().getItem();
            RequirementHolder holder = LevelLockHandler.getLocks(Item.class, item);
            if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                List<Requirement> requirements = holder.getRequirements();
                StringBuilder reqs = new StringBuilder(I18n.format("compatskills.tconstruct.toolTypeError") + "\n" + "With Requirements: ");
                for (Requirement req : requirements) {
                    reqs.append("\n").append(req.getToolTip(data));
                }
                event.setCanceled(reqs.toString());
            }
        } else {
            event.setCanceled("bananas");
        }
    }
}
