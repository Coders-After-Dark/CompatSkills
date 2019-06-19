package codersafterdark.compatskills.common.compats.astralsorcery.requirements;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import hellfirepvp.astralsorcery.common.data.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.data.research.ProgressionTier;
import hellfirepvp.astralsorcery.common.data.research.ResearchManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class AstralTierRequirement extends Requirement {
    private final ProgressionTier tier;

    public AstralTierRequirement(ProgressionTier tier) {
        this.tier = tier;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.AQUA + new TextComponentTranslation("compatskills.requirements.format.astral_tier", "%s", tier).getUnformattedComponentText();
    }

    public static AstralTierRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No progression tier given.");
        }
        try {
            return new AstralTierRequirement(ProgressionTier.valueOf(input.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RequirementException("Invalid progression tier: '" + input + "'.");
        }
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        PlayerProgress progress = player instanceof EntityPlayerMP ? ResearchManager.getProgress((EntityPlayerMP) player) : ResearchManager.clientProgress;
        return progress.getTierReached().isThisLaterOrEqual(tier);
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return equals(other) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AstralTierRequirement) {
            AstralTierRequirement other = (AstralTierRequirement) o;
            return tier.equals(other.tier);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return tier.hashCode();
    }

    @Override
    public boolean isCacheable() {
        return false;
    }
}