package codersafterdark.compatskills.common.compats.gamestages.GameStageUnlockable;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.Level;

import static codersafterdark.compatskills.utils.CompatSkillConstants.MOD_ID;

public class GameStageUnlockable extends Unlockable {
    public String gameStage;

    public GameStageUnlockable(String gameStage, String name, int x, int y, ResourceLocation skillName, int cost, String... defaultRequirements) {
        super(new ResourceLocation(MOD_ID, name), x, y, skillName, cost, defaultRequirements);
        this.gameStage = gameStage;
    }

    @Override
    public void onUnlock(EntityPlayer player) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            GameStageHelper.addStage(player, gameStage);
            player.sendStatusMessage(new TextComponentString("Unlocked GameStage: " + gameStage), true);
        } else {
            CompatSkills.logger.log(Level.ERROR, "WHY IS THE SERVER NULL!?");
        }
    }
}