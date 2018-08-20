package codersafterdark.compatskills.common.compats.thaumcraft.requirements;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.ThaumcraftApi;

public class ResearchRequirement extends Requirement {
    private final String researchKey;

    public ResearchRequirement(String researchKey) {
        this.researchKey = researchKey;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.GOLD + new TextComponentTranslation("compatskills.misc.requirements.researchRequirement", "%s", researchKey).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        return ThaumcraftApi.internalMethods.doesPlayerHaveRequisites(entityPlayer, researchKey);
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return equals(other) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ResearchRequirement && researchKey.equals(((ResearchRequirement) obj).researchKey);
    }

    @Override
    public int hashCode() {
        return researchKey.hashCode();
    }
}