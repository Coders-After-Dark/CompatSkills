package codersafterdark.compatskills.common.compats.minecraft.drops;

import codersafterdark.compatskills.utils.CompatSkillsConfig;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockDropsHandler {
    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        boolean errored = false;

        if (event.isCanceled()) {
            return;
        }

        EntityPlayer player = event.getHarvester();
        if (!ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }

        PlayerData data = PlayerDataHandler.get(player);
        Map<RequirementHolder, ItemStack> stackMap = new HashMap<>();

        for (ItemStack stack : event.getDrops()) {
            RequirementHolder temp = LevelLockHandler.getLockByKey(new ItemStackDropKey(stack));
            if (!temp.equals(LevelLockHandler.EMPTY_LOCK)) {
                stackMap.put(temp, stack);
            }
        }

        for (RequirementHolder holder : stackMap.keySet()) {
            if (!data.matchStats(holder)) {
                errored = true;
                event.getDrops().remove(stackMap.get(holder));
                if (CompatSkillsConfig.Configs.Minecraft.BlockDropsError) {
                    TextComponentTranslation error = new TextComponentTranslation("compatskills.drops.error");
                    TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
                    String reqString = holder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
                    ITextComponent textComponent = new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
                    player.sendStatusMessage(textComponent, false);
                }
            }
        }

        if (!CompatSkillsConfig.Configs.Minecraft.BlockDropsError && errored) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.drops.error");
            ITextComponent textComponent = new TextComponentString(error.getUnformattedComponentText());
            player.sendStatusMessage(textComponent, true);
        }
    }
}
