package codersafterdark.compatskills.common.compats.dynamicswordskills;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import dynamicswordskills.entity.DSSPlayerInfo;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class DSSkillRequirement extends Requirement {
    private final SkillBase skill;
    private final int level;

    public DSSkillRequirement(SkillBase skill, int level) {
        this.skill = skill;
        this.level = level;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.GOLD +  new TextComponentTranslation("compatskills.requirements.format.dss", "%s", skill.getDisplayName(),
                level).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        DSSPlayerInfo info = DSSPlayerInfo.get(player);
        return info != null && info.getSkillLevel(skill) >= level;
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o instanceof DSSkillRequirement) {
            DSSkillRequirement other = (DSSkillRequirement) o;
            if (skill.equals(other.skill)) {
                return level == other.level ? RequirementComparision.EQUAL_TO : level > other.level ? RequirementComparision.GREATER_THAN : RequirementComparision.LESS_THAN;
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DSSkillRequirement) {
            DSSkillRequirement other = (DSSkillRequirement) o;
            if (skill.equals(other.skill)) {
                return level == other.level;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, level);
    }

    public static DSSkillRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No Skill given.");
        }
        String[] parts = input.split("\\|");
        SkillBase skill = SkillBase.getSkillByName(parts[0]);
        if (skill == null) {
            throw new RequirementException("Invalid skill '" + parts[0] + "'.");
        }
        if (parts.length == 1) {
            throw new RequirementException("No level given for skill '" + skill.getDisplayName() + "'.");
        }
        try {
            return new DSSkillRequirement(skill, Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new RequirementException("Invalid level '" + parts[1] + "' for skill: '" + skill.getDisplayName() + "'.");
        }
    }
}