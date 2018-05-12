package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class ItemRequirement extends Requirement {

    private ItemStack stack;

    public ItemRequirement (ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return checkHolding(stack, entityPlayerMP);
    }

    @Override
    public String getToolTip(PlayerData data) {
        String stackDisplayName = stack.getDisplayName();
        String toolTip = "";
        TextFormatting color = TextFormatting.GREEN;
        if (!achievedByPlayer(data.playerWR.get())){
            color = TextFormatting.RED;
        }
        if (stack != null){
            toolTip = TextFormatting.GRAY + " - " + TextFormatting.GRAY + new TextComponentTranslation("compatskills.misc.requirements.itemRequirementFormat", color, stackDisplayName);
        }

        return toolTip;
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof ItemRequirement && getStack().equals(((ItemRequirement) other).getStack()) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    private boolean checkHolding(ItemStack stack, EntityPlayer player){
        return player.getHeldItemMainhand() == stack || player.getHeldItemOffhand() == stack;
    }

    private ItemStack getStack() {
        return stack;
    }
}
