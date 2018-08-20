package codersafterdark.compatskills.common.compats.thaumcraft.requirements;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.ThaumcraftApi;

public class WarpRequirement extends Requirement {
    private final int warp;

    public WarpRequirement(int warp) {
        this.warp = warp;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.DARK_PURPLE + new TextComponentTranslation("compatskills.misc.requirements.warpRequirement", "%s", warp).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return ThaumcraftApi.internalMethods.getActualWarp(entityPlayerMP) >= warp;
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        if (other instanceof WarpRequirement) {
            WarpRequirement warpRequirement = (WarpRequirement) other;
            if (warp == warpRequirement.warp) {
                return RequirementComparision.EQUAL_TO;
            }
            return warp > warpRequirement.warp ? RequirementComparision.GREATER_THAN : RequirementComparision.LESS_THAN;
        }
        return RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof WarpRequirement && warp == ((WarpRequirement) obj).warp;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(warp);
    }
}