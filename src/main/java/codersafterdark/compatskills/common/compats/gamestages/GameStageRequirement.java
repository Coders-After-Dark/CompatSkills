package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class GameStageRequirement extends Requirement {
    private String gamestage;

    public GameStageRequirement(String gamestage) {
        this.gamestage = gamestage;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        PlayerDataHandler.IStageData data = PlayerDataHandler.getStageData(entityPlayerMP);
        return data.hasUnlockedStage(gamestage);
    }

    @Override
    public String getToolTip(PlayerData playerData) {
        PlayerDataHandler.IStageData data = PlayerDataHandler.getStageData(playerData.playerWR.get());
        TextFormatting color = TextFormatting.GREEN;
        if (!data.hasUnlockedStage(gamestage)) {
            color = TextFormatting.RED;
        }
        return TextFormatting.GRAY + " - " + TextFormatting.BLUE + I18n.format("compatskills.misc.gamestageFormat", color, gamestage);
    }
}
