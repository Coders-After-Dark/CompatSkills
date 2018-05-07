package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.List;

public class DimensionLockHandler {
    @SubscribeEvent
    public void onDimensionChanged(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        PlayerData data = PlayerDataHandler.get(player);
        int dimension = event.toDim;
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new DimensionLockKey(dimension));
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            String error = I18n.format("compatskills.dimension.travelError");
            List<Requirement> requirements = requirementHolder.getRequirements();
            StringBuilder reqString = new StringBuilder(I18n.format("compatskills.misc.Requirements"));
            for (Requirement requirement : requirements) {
                reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
            }
            ITextComponent textComponent = new TextComponentString(error + ' ' + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}
