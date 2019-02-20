package codersafterdark.compatskills.common.compats.minecraft.drops;

import codersafterdark.compatskills.utils.CompatSkillsConfig;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockDropsHandler {
    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }

        EntityPlayer player = event.getHarvester();
        if (Utils.skipPlayer(player)) {
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


        boolean errored = false;
        for (Map.Entry<RequirementHolder, ItemStack> entry : stackMap.entrySet()) {
            RequirementHolder holder = entry.getKey();
            if (!data.matchStats(holder)) {
                errored = true;
                event.getDrops().remove(entry.getValue());
                if (CompatSkillsConfig.Configs.Minecraft.BlockDropsError) {
                    TextComponentTranslation error = new TextComponentTranslation("compatskills.error.drops");
                    player.sendStatusMessage(Utils.getError(holder, data, error), false);
                }
            }
        }

        if (!CompatSkillsConfig.Configs.Minecraft.BlockDropsError && errored) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.drops");
            player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText()), true);
        }
    }
}