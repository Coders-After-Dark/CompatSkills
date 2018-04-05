package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GameStageUnlockable extends Unlockable {
    public String gameStage;

    public GameStageUnlockable(String gameStage, ResourceLocation name, int x, int y, ResourceLocation skillName, int cost, String... defaultRequirements) {
        super(name, x, y, skillName, cost, defaultRequirements);
        this.gameStage = gameStage;
    }

    @Override
    public void onUnlock(EntityPlayer player) {
        FMLCommonHandler.instance().getMinecraftServerInstance().commandManager.executeCommand(player, "gamestage add " + player.getName() + " " + gameStage);
    }
}
