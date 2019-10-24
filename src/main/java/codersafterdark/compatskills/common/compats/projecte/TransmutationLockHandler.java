package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.base.ToolTipHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.event.PlayerAttemptCondenserSetEvent;
import moze_intel.projecte.api.event.PlayerAttemptLearnEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TransmutationLockHandler {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void onAttemptCondenserSet(PlayerAttemptCondenserSetEvent event) {
        if (!event.isCanceled()) {
            EntityPlayer player = event.getPlayer();
            if (Utils.skipPlayer(player)) {
                return;
            }
            PlayerData data = PlayerDataHandler.get(player);
            RequirementHolder holder = itemRequirements(event.getStack());
            if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                event.setCanceled(true);
                if (player.getEntityWorld().isRemote) {
                    String reqs = holder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
                    TextComponentTranslation error = new TextComponentTranslation("compatskills.error.projecte.condenser");
                    player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
                }
            }
        }
    }

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void onAttemptLearnEvent(PlayerAttemptLearnEvent event) {
        if (!event.isCanceled()) {
            EntityPlayer player = event.getPlayer();
            if (Utils.skipPlayer(player)) {
                return;
            }
            PlayerData data = PlayerDataHandler.get(player);
            RequirementHolder holder = itemRequirements(event.getStack());
            if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                event.setCanceled(true);
                if (player.getEntityWorld().isRemote) {
                    String reqs = holder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
                    TextComponentTranslation error = new TextComponentTranslation("compatskills.error.projecte.learn");
                    player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
                }
            }
        }
    }

    private RequirementHolder itemRequirements(ItemStack stack) {
        EMCLockKey lockKey = new EMCLockKey(ProjectEAPI.getEMCProxy().getValue(stack));
        RequirementHolder emcHolder = LevelLockHandler.getLockByFuzzyKey(lockKey);
        RequirementHolder itemHolder = LevelLockHandler.getSkillLock(stack);
        if (emcHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
            return itemHolder;
        } else if (itemHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
            return emcHolder;
        } //else
        return new RequirementHolder(itemHolder, emcHolder);
    }

    //TODO should this have sideonly annotation
    public List<String> transmutationTooltip(ToolTipHandler.ToolTipInfo info) {
        return emcTooltip(info, TextFormatting.DARK_RED + new TextComponentTranslation("compatskills.warn.projecte.transmutation").getUnformattedComponentText());
    }

    //TODO should this have sideonly annotation
    public List<String> condenserTooltip(ToolTipHandler.ToolTipInfo info) {
        return emcTooltip(info, TextFormatting.YELLOW + new TextComponentTranslation("compatskills.warn.projecte.condenser").getUnformattedComponentText());
    }

    private List<String> emcTooltip(ToolTipHandler.ToolTipInfo info, String message) {
        List<String> toolTip = new ArrayList<>();
        PlayerData data = info.getData();
        RequirementHolder holder = itemRequirements(info.getItem());
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            long cost = ProjectEAPI.getEMCProxy().getValue(info.getItem());
            RequirementHolder emcHolder = LevelLockHandler.getLockByFuzzyKey(new EMCLockKey(cost));
            if (!emcHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
                emcHolder.getRequirements().stream().map(requirement -> requirement.getToolTip(data)).forEach(toolTip::add);
            }
            if (cost > 0) {
                toolTip.add(message);
            }
        }
        return toolTip;
    }
}