package codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class GameStageRequirement extends Requirement {
    private String gamestage;

    public GameStageRequirement(String gamestage) {
        this.gamestage = gamestage;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        return GameStageHelper.hasStage(entityPlayer, gamestage);
    }

    @Override
    public String getToolTip(PlayerData playerData) {
        TextFormatting color = TextFormatting.GREEN;
        if (playerData.playerWR.get() != null){
            if (!GameStageHelper.hasStage(playerData.playerWR.get(), gamestage)) {
                color = TextFormatting.RED;
            }
        }
        return TextFormatting.GRAY + " - " + TextFormatting.BLUE + I18n.format("compatskills.misc.gamestageFormat", color, gamestage);
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof GameStageRequirement && gamestage.equals(((GameStageRequirement) other).gamestage)
                ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
}
