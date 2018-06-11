package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class DimensionRequirement extends Requirement {
    private final int dimension;

    public DimensionRequirement(int dimension) {
        this.dimension = dimension;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("compatskills.misc.dimensionFormat", "%s", dimension).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return entityPlayerMP.dimension == dimension;
    }
    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof DimensionRequirement && dimension == ((DimensionRequirement) other).dimension ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof DimensionRequirement && dimension == ((DimensionRequirement) o).dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension);
    }
}