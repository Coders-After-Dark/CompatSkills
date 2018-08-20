package codersafterdark.compatskills.common.compats.thaumcraft.requirements;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.ThaumcraftApi;

public class ResearchRequirement extends Requirement {
    private final String researchKey;

    public ResearchRequirement(String researchKey) {
        this.researchKey = researchKey;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        return ThaumcraftApi.internalMethods.doesPlayerHaveRequisites(entityPlayer, researchKey);
    }

    // Pup needs to fix the two below methods.
    @Override
    public RequirementComparision matches(Requirement other) {
        if (other instanceof ResearchRequirement) {
            ResearchRequirement o = (ResearchRequirement) other;
        }
        return super.matches(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof ResearchRequirement) {
            ResearchRequirement other = (ResearchRequirement) obj;
            if (researchKey.equals(other.researchKey)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return researchKey.hashCode();
    }
}
