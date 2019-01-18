package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.common.compats.oreexcavator.filter.ExcavateRequirementFilter;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import oreexcavation.events.EventExcavate;
import oreexcavation.handlers.MiningAgent;

public class ExcavationLockHandler {
    @SubscribeEvent
    public void onExcavation(EventExcavate.Pre event) {
        MiningAgent agent = event.getAgent();
        EntityPlayer player = agent.player;
        PlayerData data = PlayerDataHandler.get(player);

        RequirementHolder holder = OreExcavationCompatHandler.getHolder();
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.excavation.general.error");
            player.sendStatusMessage(Utils.getError(holder, data, error), true);
            event.setCanceled(true);
        } else {
            RequirementHolder shapeHolder = LevelLockHandler.getLockByKey(new ExcavationShapeKey(agent.shape.getName()));
            if (!shapeHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(shapeHolder)) {
                event.setCanceled(true);
                TextComponentTranslation error = new TextComponentTranslation("compatskills.excavatation.shape.error");
                player.sendStatusMessage(Utils.getError(holder, data, error), true);
            } else {
                IBlockState state = player.getEntityWorld().getBlockState(agent.origin);
                ItemStack stack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
                if (!LevelLockHandler.canPlayerUseItem(player, stack)) {
                    event.setCanceled(true);
                    TextComponentTranslation error = new TextComponentTranslation("compatskills.excavation.block.error");
                    player.sendStatusMessage(Utils.getError(holder, data, error), false);
                } else {
                    // Even though we are cancelling the event if the initial mined block can't be harvested due to requirements
                    // We should add this filter in-case a dev adds several things to tiers so they don't mine a lower requirement ore and end-up mining higher requirement ores.
                    agent.addFilter(new ExcavateRequirementFilter());
                }
            }
        }
    }
}