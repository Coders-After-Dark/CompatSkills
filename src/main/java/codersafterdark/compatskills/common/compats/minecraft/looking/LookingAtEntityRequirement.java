package codersafterdark.compatskills.common.compats.minecraft.looking;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class LookingAtEntityRequirement extends Requirement {
    private String entityID;

    public LookingAtEntityRequirement(String entityID, String name) {
        this.entityID = entityID;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.DARK_GREEN + new TextComponentTranslation("compatskills.requirements.format.looking_at", "%s", name).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        if (player == null) {
            return false;
        }
        Entity entity = Utils.lookingAt(player);
        if (entity != null) {
            return entityID.equals(Utils.getEntityID(entity));
        }
        return false;
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        return o instanceof LookingAtEntityRequirement && entityID.equals(((LookingAtEntityRequirement) o).entityID) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof LookingAtEntityRequirement && entityID.equals(((LookingAtEntityRequirement) obj).entityID);
    }

    @Override
    public int hashCode() {
        return entityID.hashCode();
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    public static LookingAtEntityRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No Entity given.");
        }
        String translationName = EntityList.getTranslationName(new ResourceLocation(input));
        if (translationName == null) {
            throw new RequirementException("Unable to find entity: '" + input + "'.");
        }
        return new LookingAtEntityRequirement(input, new TextComponentTranslation(translationName).getUnformattedComponentText());
    }
}