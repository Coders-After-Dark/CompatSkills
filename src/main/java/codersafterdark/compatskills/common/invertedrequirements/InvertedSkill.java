package codersafterdark.compatskills.common.invertedrequirements;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class InvertedSkill extends Requirement {
    private final Skill skill;
    private final int level;

    public InvertedSkill(Skill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        PlayerData data = PlayerDataHandler.get(entityPlayer);
        return data.getSkillInfo(skill).getLevel() < level;
    }

    @Override
    public String getToolTip(PlayerData data) {
        TextFormatting color = TextFormatting.GREEN;
        if (data == null || data.getSkillInfo(skill).getLevel() >= level) {
            color = TextFormatting.RED;
        }
        return TextFormatting.GRAY + " - " + new TextComponentTranslation("compatskills.misc.requirements.invertedSkillFormat", TextFormatting.DARK_AQUA, skill.getName(), color, level).getUnformattedComponentText();
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        if (other instanceof InvertedSkill) {
            InvertedSkill skillRequirement = (InvertedSkill) other;
            if (skill == null || skillRequirement.skill == null) {
                //If they are both invalid don't bother checking the level.
                return RequirementComparision.NOT_EQUAL;
            }
            if (skill.getKey().equals(skillRequirement.skill.getKey())) {
                if (level == skillRequirement.level) {
                    return RequirementComparision.EQUAL_TO;
                }
                //Greater than means it is the "more restrictive" one which we want to be smaller
                return level < skillRequirement.level ? RequirementComparision.GREATER_THAN : RequirementComparision.LESS_THAN;
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean isEnabled() {
        return skill != null && skill.isEnabled();
    }
}
