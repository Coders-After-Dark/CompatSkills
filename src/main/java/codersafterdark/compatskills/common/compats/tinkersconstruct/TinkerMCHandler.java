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

import static codersafterdark.compatskills.common.compats.tinkersconstruct.TinkerLockHandler.getCanceledMessage;

public class TinkerMCHandler {
    @SubscribeEvent
    public void onToolDamageModified(TinkerCraftingEvent.ToolModifyEvent event) {
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }

        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            event.setCanceled(true);
            return;
        }

        RequirementHolder holder = LevelLockHandler.getSkillLock(event.getItemStack());
        if (holder.equals(LevelLockHandler.EMPTY_LOCK)) {
            return;
        }

        String canceledMessage = getCanceledMessage(data, holder, new TextComponentTranslation("compatskills.error.tconstruct.modifier").getUnformattedComponentText());
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }
}
