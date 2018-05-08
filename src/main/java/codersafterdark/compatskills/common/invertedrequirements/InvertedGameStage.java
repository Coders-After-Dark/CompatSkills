package codersafterdark.compatskills.common.invertedrequirements;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class InvertedGameStage extends Requirement {
    private String gamestage;

    public InvertedGameStage(String gamestage) {
        this.gamestage = gamestage;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        return !GameStageHelper.hasStage(entityPlayer, gamestage);
    }

    @Override
    public String getToolTip(PlayerData playerData) {
        TextFormatting color = TextFormatting.GREEN;
        if (playerData.playerWR.get() != null) {
            if (GameStageHelper.hasStage(playerData.playerWR.get(), gamestage)) {
                color = TextFormatting.RED;
            }
        }
        return TextFormatting.GRAY + " - " + TextFormatting.BLUE + new TextComponentTranslation("compatskills.misc.requirements.invertedGamestageFormat", color, gamestage).getUnformattedComponentText();
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof InvertedGameStage && gamestage.equals(((InvertedGameStage) other).gamestage)
                ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
}
