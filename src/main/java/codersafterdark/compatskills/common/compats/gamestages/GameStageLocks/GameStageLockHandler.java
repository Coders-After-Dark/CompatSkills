package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import com.google.common.collect.Maps;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class GameStageLockHandler {
    private Map<String, RequirementHolder> gameStageLockMap;

    public GameStageLockHandler(){
        gameStageLockMap = Maps.newHashMap();
    }

    public void addGameStageLock(GameStageLock lock) {
        gameStageLockMap.put(lock.getGamestage(), lock.getHolder());
    }

    @SubscribeEvent
    public void gameStageAdded(GameStageEvent.Add event){
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        String eventGameStage = event.getStageName();
        if (gameStageLockMap.containsKey(eventGameStage)){
            if (!data.matchStats(gameStageLockMap.get(eventGameStage))){
                event.setCanceled(true);
                String error = I18n.format("compatskills.gamestage.addError");
                List<Requirement> requirements = gameStageLockMap.get(eventGameStage).getRequirements();
                StringBuilder reqString = new StringBuilder(I18n.format("compatskills.misc.Requirements"));
                for (Requirement requirement : requirements){
                    reqString.append("\n ").append(requirement.getToolTip(data)).append(" ");
                }
                ITextComponent textComponent = new TextComponentString(error + " " + reqString);
                player.sendStatusMessage(textComponent, false);
            }
        }
    }
}
