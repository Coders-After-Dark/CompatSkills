package codersafterdark.compatskills.common.compats.minecraft.health;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class HealthRequirement extends Requirement {
    private final double percent;

    public HealthRequirement(double percent) {
        this.percent = percent;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.DARK_GREEN + new TextComponentTranslation("compatskills.requirements.format.health", "%s", percent).getUnformattedComponentText();
    }

    public static HealthRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No health percentage given.");
        }
        try {
            return new HealthRequirement(Double.parseDouble(input));
        } catch (NumberFormatException e) {
            throw new RequirementException("Invalid health percentage '" + input + "'.");
        }
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return entityPlayerMP.getHealth() / entityPlayerMP.getMaxHealth() >= percent;
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o == this) {
            return RequirementComparision.EQUAL_TO;
        }
        if (o instanceof HealthRequirement) {
            HealthRequirement other = (HealthRequirement) o;
            if (percent == other.percent) {
                return RequirementComparision.EQUAL_TO;
            } else if (percent > other.percent) {
                return RequirementComparision.GREATER_THAN;
            } else if (percent < other.percent) {
                return RequirementComparision.LESS_THAN;
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof HealthRequirement && percent == ((HealthRequirement) o).percent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percent);
    }
}