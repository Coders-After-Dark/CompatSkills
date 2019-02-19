package codersafterdark.compatskills.common.compats.astralsorcery.requirements;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import hellfirepvp.astralsorcery.common.constellation.ConstellationRegistry;
import hellfirepvp.astralsorcery.common.constellation.IMajorConstellation;
import hellfirepvp.astralsorcery.common.data.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.data.research.ResearchManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class AttunedRequirement extends Requirement {
    private final IMajorConstellation constellation;

    public AttunedRequirement(IMajorConstellation constellation) {
        this.constellation = constellation;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.YELLOW + new TextComponentTranslation("compatskills.requirements.format.attuned", "%s", constellation.getSimpleName()).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        PlayerProgress progress = player instanceof EntityPlayerMP ? ResearchManager.getProgress((EntityPlayerMP) player) : ResearchManager.clientProgress;
        return constellation.equals(progress.getAttunedConstellation());
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return equals(other) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof AttunedRequirement && constellation.equals(((AttunedRequirement) o).constellation);
    }

    @Override
    public int hashCode() {
        return constellation.hashCode();
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    public static AttunedRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No major constellation given.");
        }
        IMajorConstellation constellation = ConstellationRegistry.getMajorConstellationByName(input);
        if (constellation == null) {
            throw new RequirementException("Could not find major constellation: '" + input+ "'.");
        }
        return new AttunedRequirement(constellation);
    }
}