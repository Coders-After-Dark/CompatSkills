package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GameStageUnlockable extends Unlockable{
    private String gameStage;

    public GameStageUnlockable(ResourceLocation name, int x, int y, ResourceLocation skillName, int cost, String... defaultRequirements) {
        super(name, x, y, skillName, cost, defaultRequirements);
    }

    @Override
    public void onUnlock(EntityPlayer player){
        PlayerDataHandler.getStageData(player).unlockStage(gameStage);
    }

    public void setGameStage(String gameStage) {
        this.gameStage = gameStage;
    }
}
