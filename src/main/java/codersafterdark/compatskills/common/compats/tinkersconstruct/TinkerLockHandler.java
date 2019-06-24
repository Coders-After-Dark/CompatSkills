package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;

public class TinkerLockHandler {

    @SubscribeEvent
    public void onCraftingMaterial(TinkerCraftingEvent.ToolPartCraftingEvent event) {
        handleTinkerEvent(event, "compatskills.error.tconstruct.craft.part");
    }

    @SubscribeEvent
    public void onModifierAttached(TinkerCraftingEvent.ToolModifyEvent event) {
        handleTinkerEvent(event, "compatskills.error.tconstruct.modify");
    }

    @SubscribeEvent
    public void onPartReplaced(TinkerCraftingEvent.ToolPartReplaceEvent event) {
        handleTinkerEvent(event, "compatskills.error.tconstruct.modify");
    }

    @SubscribeEvent
    public void onToolCrafted(TinkerCraftingEvent.ToolCraftingEvent event) {
        if (event.getPlayer() == null) {
            //Legacy support for older versions of tinkers where player could be null when first opening the table.
            // Newer versions should not have this ever be the case
            event.setCanceled(true);
            return;
        }
        handleTinkerEvent(event, "compatskills.error.tconstruct.craft.tool");
    }

    private void handleTinkerEvent(TinkerCraftingEvent event, String translationKey) {
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            //Should not be the case given skipPlayer did not return true but just in case
            return;
        }
        RequirementHolder holder = LevelLockHandler.getSkillLock(event.getItemStack());
        if (holder.equals(LevelLockHandler.EMPTY_LOCK) || data.matchStats(holder)) {
            return;
        }
        StringBuilder reqs = new StringBuilder(new TextComponentTranslation(translationKey).getUnformattedComponentText());
        reqs.append("\n\n").append(new TextComponentTranslation("compatskills.misc.requirements").getUnformattedComponentText());
        holder.getRequirements().forEach(req -> reqs.append('\n').append(req.getToolTip(data)));
        event.setCanceled(reqs.toString());
    }
}