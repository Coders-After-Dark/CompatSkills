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

import java.util.List;
import java.util.stream.Collectors;

public class ExcavationLockHandler {

    public static String[] requirements;

    @SubscribeEvent
    public void onExcavation(EventExcavate.Pre event) {
        MiningAgent agent = event.getAgent();
        EntityPlayer player = agent.player;
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder holder = RequirementHolder.fromStringList(requirements);
        if (!data.matchStats(holder)) {
            TextComponentTranslation error = new TextComponentTranslation("compatskills.excavation.general.error");
            TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
            String reqString = holder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
            ITextComponent component = new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
            player.sendStatusMessage(component, true);
        } else {
            RequirementHolder shapeHolder = LevelLockHandler.getLockByKey(new ExcavationShapeKey(agent.shape.getName()));
            if (!data.matchStats(shapeHolder)) {
                event.setCanceled(true);
                TextComponentTranslation error = new TextComponentTranslation("compatskills.excavatation.shape.error");
                TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
                String reqString = holder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
                ITextComponent component = new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
                player.sendStatusMessage(component, true);
            }
            agent.addFilter(new ExcavateRequirementFilter());
        }
    }
}
