package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.common.compats.oreexcavator.filter.ExcavateRequirementFilter;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import oreexcavation.events.EventExcavate;
import oreexcavation.handlers.MiningAgent;

import java.util.stream.Collectors;

public class ExcavationLockHandler {
    @SubscribeEvent
    public void onExcavation(EventExcavate.Pre event) {
        MiningAgent agent = event.getAgent();
        EntityPlayer player = agent.player;
        PlayerData data = PlayerDataHandler.get(player);

        RequirementHolder holder = OreExcavatorCompatHandler.getHolder();
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.excavation.general.error");
            player.sendStatusMessage(getError(error, data, holder), true);
        } else {
            RequirementHolder shapeHolder = LevelLockHandler.getLockByKey(new ExcavationShapeKey(agent.shape.getName()));
            if (!shapeHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(shapeHolder)) {
                event.setCanceled(true);
                TextComponentTranslation error = new TextComponentTranslation("compatskills.excavatation.shape.error");
                player.sendStatusMessage(getError(error, data, holder), true);
            }
            agent.addFilter(new ExcavateRequirementFilter());
        }
    }

    private ITextComponent getError(TextComponentTranslation error, PlayerData data, RequirementHolder holder) {
        TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
        String reqString = holder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
        return new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
    }
}