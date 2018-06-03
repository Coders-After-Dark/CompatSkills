package codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class HarvestLevelRequirement extends Requirement {
    private final int harvestLevel;

    public HarvestLevelRequirement(int level) {
        this.harvestLevel = level;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        return hasHarvestLevel(player.getHeldItemMainhand()) || hasHarvestLevel(player.getHeldItemOffhand());
    }

    @Override
    public String getToolTip(PlayerData data) {
        TextFormatting color = data == null || !achievedByPlayer(data.playerWR.get()) ? TextFormatting.RED : TextFormatting.GREEN;
        return TextFormatting.GRAY + " - " + new TextComponentTranslation("compatskills.misc.requirements.harvestLevelRequirementFormat", color, harvestLevel).getUnformattedComponentText();
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o instanceof HarvestLevelRequirement) {
            HarvestLevelRequirement other = (HarvestLevelRequirement) o;
            if (harvestLevel == other.harvestLevel) {
                return RequirementComparision.EQUAL_TO;
            }
            return harvestLevel > other.harvestLevel ? RequirementComparision.GREATER_THAN : RequirementComparision.LESS_THAN;
        }
        return RequirementComparision.NOT_EQUAL;
    }

    private boolean hasHarvestLevel(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        Item item = stack.getItem();
        return item.getToolClasses(stack).stream().anyMatch(toolClass -> item.getHarvestLevel(stack, toolClass, null, null) >= harvestLevel);
    }
}