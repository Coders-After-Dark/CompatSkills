package codersafterdark.compatskills.common.compats.tinkersconstruct.modifierLocks;

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
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.List;

public class ModifierLockHandler {
    @SubscribeEvent
    public void onModifierAttached(TinkerCraftingEvent.ToolModifyEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        for (final IModifier modifier : event.getModifiers()) {
            RequirementHolder holder = LevelLockHandler.getLocks(IModifier.class, modifier);
            if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                event.setCanceled(true);
                List<Requirement> requirements = holder.getRequirements();
                StringBuilder reqs = new StringBuilder(I18n.format("compatskills.tconstruct.modifierError") + "\n" + "With Requirements: ");
                for (Requirement req : requirements) {
                    reqs.append("\n").append(req.getToolTip(data));
                }
                player.sendStatusMessage(new TextComponentString(reqs.toString()), false);
            }
        }
    }
}
