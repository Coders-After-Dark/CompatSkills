package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class GameStageLockHandler {
    @SubscribeEvent
    public void gameStageAdded(GameStageEvent.Add event){
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        String eventGameStage = event.getStageName();
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new GameStageLock(eventGameStage));
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
            if (!data.matchStats(requirementHolder)){
                event.setCanceled(true);
                String error = I18n.format("compatskills.gamestage.addError");
                List<Requirement> requirements = requirementHolder.getRequirements();
                StringBuilder reqString = new StringBuilder(I18n.format("compatskills.misc.Requirements"));
                for (Requirement requirement : requirements) {
                    reqString.append("\n ").append(requirement.getToolTip(data)).append(" ");
                }
                ITextComponent textComponent = new TextComponentString(error + " " + reqString);
                player.sendStatusMessage(textComponent, false);
            }
        }
    }
}
