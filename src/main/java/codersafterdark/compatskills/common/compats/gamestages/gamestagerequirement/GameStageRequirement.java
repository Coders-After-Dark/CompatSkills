package codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class GameStageRequirement extends Requirement {
    private final String gamestage;

    public GameStageRequirement(String gamestage) {
        this.gamestage = gamestage;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        return GameStageHelper.hasStage(player, gamestage);
    }

    @Override
    public String getToolTip(PlayerData data) {
        TextFormatting color = data == null || !data.requirementAchieved(this) ? TextFormatting.RED : TextFormatting.GREEN;
        return TextFormatting.GRAY + " - " + TextFormatting.BLUE + new TextComponentTranslation("compatskills.misc.gamestageFormat", color, gamestage).getUnformattedComponentText();
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof GameStageRequirement && gamestage.equals(((GameStageRequirement) other).gamestage)
                ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
}