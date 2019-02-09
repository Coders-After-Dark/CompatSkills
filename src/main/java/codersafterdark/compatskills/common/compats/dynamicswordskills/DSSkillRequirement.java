package codersafterdark.compatskills.common.compats.dynamicswordskills;

import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
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
}