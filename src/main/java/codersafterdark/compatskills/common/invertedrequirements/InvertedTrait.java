package codersafterdark.compatskills.common.invertedrequirements;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.TraitRequirement;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InvertedTrait extends Requirement {
    private Unlockable unlockable;

    public InvertedTrait(ResourceLocation traitName) {
        this.unlockable = ReskillableRegistries.UNLOCKABLES.getValue(traitName);
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        PlayerData data = PlayerDataHandler.get(entityPlayer);
        return data.getSkillInfo(unlockable.getParentSkill()).isUnlocked(unlockable);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getToolTip(PlayerData data) {
        Unlockable unlockable = getUnlockable();
        TextFormatting color = TextFormatting.GREEN;
        String name = "";

        if (unlockable != null) {
            if (data.getSkillInfo(unlockable.getParentSkill()).isUnlocked(unlockable)) {
                color = TextFormatting.RED;
            }
            name = unlockable.getName();
        }
        return TextFormatting.GRAY + " - " + TextFormatting.LIGHT_PURPLE + I18n.format("compatskills.misc.requirements.invertedTraitFormat", color, name);
    }

    public Skill getSkill() {
        return unlockable.getParentSkill();
    }

    public Unlockable getUnlockable() {
        return unlockable;
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        if (other instanceof TraitRequirement) {
            InvertedTrait invertedTrait = (InvertedTrait) other;
            if (getUnlockable() == null) {
                return invertedTrait.getUnlockable() == null ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
            }
            return invertedTrait.getUnlockable() != null && getUnlockable().getKey().equals(invertedTrait.getUnlockable().getKey()) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
        }
        return RequirementComparision.NOT_EQUAL;
    }
}
