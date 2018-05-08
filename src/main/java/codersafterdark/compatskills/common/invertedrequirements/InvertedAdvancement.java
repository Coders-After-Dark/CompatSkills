package codersafterdark.compatskills.common.invertedrequirements;

import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Optional;

public class InvertedAdvancement extends Requirement {
    private ResourceLocation advancementName;

    public InvertedAdvancement(ResourceLocation advancementName) {
        this.advancementName = advancementName;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayer) {
        return !Optional.ofNullable(this.getAdvancement())
                .map(advancement -> ReskillableAPI.getInstance().getAdvancementProgress(entityPlayer, advancement))
                .map(AdvancementProgress::isDone)
                .orElse(false);
    }

    @Override
    public String getToolTip(PlayerData data) {
        Advancement adv = getAdvancement();
        String toolTip = "";
        if (adv != null) {
            toolTip = TextFormatting.GRAY + " - " + TextFormatting.GOLD + new TextComponentTranslation("compatskills.misc.requirements.invertedAchievementFormat",
                    adv.getDisplayText()
                            .getUnformattedText()
                            .replaceAll("[\\[\\]]", ""));
        }
        return toolTip;
    }

    public Advancement getAdvancement() {
        return RequirementHolder.getAdvancementList().getAdvancement(advancementName);
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof InvertedAdvancement && getAdvancement().equals(((InvertedAdvancement) other).getAdvancement())
                ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
}
