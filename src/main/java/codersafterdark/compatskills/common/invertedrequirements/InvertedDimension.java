package codersafterdark.compatskills.common.invertedrequirements;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class InvertedDimension extends Requirement {
    private int dimension;

    public InvertedDimension(int dimension) {
        this.dimension = dimension;
    }


    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return entityPlayerMP.dimension != dimension;
    }

    @Override
    public String getToolTip(PlayerData data) {
        TextFormatting color = TextFormatting.GREEN;
        if (data == null) {
            color = TextFormatting.RED;
        } else {
            EntityPlayer player = data.playerWR.get();
            if (player != null && player.dimension == dimension) {
                color = TextFormatting.RED;
            }
        }
        return TextFormatting.GRAY + " - " + TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("compatskills.misc.requirements.invertedDimensionFormat", color, dimension).getUnformattedComponentText();
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof InvertedDimension && dimension == ((InvertedDimension) other).dimension ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
}
