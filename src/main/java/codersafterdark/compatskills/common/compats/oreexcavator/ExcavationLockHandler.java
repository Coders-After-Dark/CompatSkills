package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.common.compats.oreexcavator.filter.ExcavateRequirementFilter;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import oreexcavation.events.EventExcavate;
import oreexcavation.handlers.MiningAgent;

public class ExcavationLockHandler {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void onExcavation(EventExcavate.Pre event) {
        MiningAgent agent = event.getAgent();
        EntityPlayer player = agent.player;
        PlayerData data = PlayerDataHandler.get(player);

        RequirementHolder holder = OreExcavationCompatHandler.getHolder();
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.ore_excavation");
            player.sendStatusMessage(Utils.getError(holder, data, error), false);
            event.setCanceled(true);
        } else {
            // Even though we cancel on interact we should add this filter in-case a dev adds several things to
            // tiers so they don't mine a lower requirement ore and end-up mining higher requirement ores.
            agent.addFilter(new ExcavateRequirementFilter());
        }
    }
}