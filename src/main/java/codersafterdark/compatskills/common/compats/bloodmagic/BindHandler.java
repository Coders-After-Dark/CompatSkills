package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.event.ItemBindEvent;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.List;

public class BindHandler {
    @SubscribeEvent
    public void bindEvent(ItemBindEvent event) {
        ItemStack stack = event.getBindingStack();
        EntityPlayer player = event.getNewOwner();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getSkillLock(stack);
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            List<Requirement> requirements = requirementHolder.getRequirements();
            StringBuilder reqs = new StringBuilder(I18n.format("compatskills.bloodmagic.bindingError") + "\n" + "With Requirements: ");
            for (Requirement req : requirements){
                reqs.append("\n").append(req.getToolTip(data));
            }
            player.sendStatusMessage(new TextComponentString(reqs.toString()), false);
        }
    }
}