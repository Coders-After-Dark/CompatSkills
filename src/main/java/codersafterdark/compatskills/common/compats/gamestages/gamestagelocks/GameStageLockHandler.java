package codersafterdark.compatskills.common.compats.gamestages.gamestagelocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class GameStageLockHandler {
    @SubscribeEvent
    public void gameStageAdded(GameStageEvent.Add event) {
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        String eventGameStage = event.getStageName();
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new GameStageLock(eventGameStage));
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            List<Requirement> requirements = requirementHolder.getRequirements();
            TextComponentTranslation error = new TextComponentTranslation("compatskills.gamestage.addError");
            TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
            StringBuilder reqString = new StringBuilder();
            for (Requirement requirement : requirements) {
                reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
            }
            ITextComponent textComponent = new TextComponentString(error + " " + error2 + " " + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}
