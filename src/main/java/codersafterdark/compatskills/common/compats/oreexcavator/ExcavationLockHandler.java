package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.common.compats.oreexcavator.filter.ExcavateRequirementFilter;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
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

        RequirementHolder holder = OreExcavatorCompatHandler.getHolder();
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.excavation.general.error");
            player.sendStatusMessage(Utils.getError(holder, data, error), true);
        } else {
            RequirementHolder shapeHolder = LevelLockHandler.getLockByKey(new ExcavationShapeKey(agent.shape.getName()));
            if (!shapeHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(shapeHolder)) {
                event.setCanceled(true);
                TextComponentTranslation error = new TextComponentTranslation("compatskills.excavatation.shape.error");
                player.sendStatusMessage(Utils.getError(holder, data, error), true);
            }
            agent.addFilter(new ExcavateRequirementFilter());
        }
    }
}