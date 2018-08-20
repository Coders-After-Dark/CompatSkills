package codersafterdark.compatskills.common.compats.thaumcraft.requirements;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.ThaumcraftApi;

public class FluxRequirement extends Requirement {
    private final int flux;

    public FluxRequirement(int flux) {
        this.flux = flux;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return flux == ThaumcraftApi.internalMethods.getActualWarp(entityPlayerMP);
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return super.matches(other);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(flux);
    }
}
