package codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;

import java.util.List;

public class ToolTypeLockHandler {
    @SubscribeEvent
    public static void onToolCrafted(TinkerCraftingEvent.ToolCraftingEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder holder = LevelLockHandler.getSkillLock(event.getItemStack());
        if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            event.setCanceled(true);
            List<Requirement> requirements = holder.getRequirements();
            StringBuilder reqs = new StringBuilder(I18n.format("compatskills.tconstruct.toolTypeError") + "\n" + "With Requirements: ");
            for (Requirement req : requirements) {
                reqs.append("\n").append(req.getToolTip(data));
            }
            player.sendStatusMessage(new TextComponentString(reqs.toString()), false);
        }
    }
}
