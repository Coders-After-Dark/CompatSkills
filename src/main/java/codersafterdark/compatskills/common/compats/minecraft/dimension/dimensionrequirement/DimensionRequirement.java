package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

//TODO: Transition this over to using dimension names
public class DimensionRequirement extends Requirement {
    private final int dimension;

    public DimensionRequirement(int dimension) {
        this.dimension = dimension;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("compatskills.requirements.format.dimension", "%s", dimension).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return entityPlayerMP.dimension == dimension;
    }
    @Override
    public RequirementComparision matches(Requirement other) {
        return equals(other) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof DimensionRequirement && dimension == ((DimensionRequirement) o).dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension);
    }

    public static DimensionRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No dimension id given.");
        }
        try {
            return new DimensionRequirement(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            throw new RequirementException("Invalid dimension id '" + input + "'.");
        }
    }
}