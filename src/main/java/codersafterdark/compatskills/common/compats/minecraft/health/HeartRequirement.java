package codersafterdark.compatskills.common.compats.minecraft.health;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class HeartRequirement extends Requirement {
    private final int hearts;

    public HeartRequirement(int hearts) {
        this.hearts = hearts;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.YELLOW + new TextComponentTranslation("compatskills.requirements.format.hearts", "%s", hearts).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return entityPlayerMP.getHealth() >= hearts;
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o == this) {
            return RequirementComparision.EQUAL_TO;
        }
        if (o instanceof HeartRequirement) {
            HeartRequirement other = (HeartRequirement) o;
            if (hearts == other.hearts) {
                return RequirementComparision.EQUAL_TO;
            } else if (hearts > other.hearts) {
                return RequirementComparision.GREATER_THAN;
            } else if (hearts < other.hearts) {
                return RequirementComparision.LESS_THAN;
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof HeartRequirement && hearts == ((HeartRequirement) o).hearts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hearts);
    }
}