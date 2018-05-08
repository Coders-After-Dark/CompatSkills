package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class DimensionRequirement extends Requirement {
    private int dimension;

    public DimensionRequirement(int dimension){
        this.dimension = dimension;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return entityPlayerMP.dimension == dimension;
    }

    @Override
    public String getToolTip(PlayerData data) {
        TextFormatting color = TextFormatting.GREEN;
        EntityPlayer player = data.playerWR.get();
        if (player != null){
            if (player.dimension != dimension){
                color = TextFormatting.RED;
            }
        }
        return TextFormatting.GRAY + " - " + TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("compatskills.misc.dimensionFormat", color, dimension);
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof DimensionRequirement && dimension == ((DimensionRequirement) other).dimension ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
}
