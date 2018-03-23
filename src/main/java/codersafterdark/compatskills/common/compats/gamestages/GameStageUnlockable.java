package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class GameStageUnlockable extends Unlockable {
    public String gameStage;

    public GameStageUnlockable(String gameStage, ResourceLocation name, int x, int y, ResourceLocation skillName, int cost, String... defaultRequirements) {
        super(name, x, y, skillName, cost, defaultRequirements);
        this.gameStage = gameStage;
    }

    @Override
    public void onUnlock(EntityPlayer player) {
        PlayerDataHandler.getStageData(player).unlockStage(gameStage);
        player.sendStatusMessage(new TextComponentString("You have unlocked Stage: " + gameStage), false);
    }
}
