package codersafterdark.compatskills.common.compats.gamestages.gamestagelocks;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GameStageLockHandler {
    @SubscribeEvent
    public void gameStageAdded(GameStageEvent.Add event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        String eventGameStage = event.getStageName();
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new GameStageLock(eventGameStage));
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.gamestage");
            player.sendStatusMessage(Utils.getError(requirementHolder, data, error), false);
        }
    }
}