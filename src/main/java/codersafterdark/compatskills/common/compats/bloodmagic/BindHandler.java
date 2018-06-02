package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.event.ItemBindEvent;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.stream.Collectors;

public class BindHandler {
    @SubscribeEvent
    public void bindEvent(ItemBindEvent event) {
        ItemStack stack = event.getBindingStack();
        EntityPlayer player = event.getNewOwner();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getSkillLock(stack);
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.bloodmagic.bindingError");
            String reqs = requirementHolder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
            player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + "\nWith Requirements: " + reqs), false);
        }
    }
}