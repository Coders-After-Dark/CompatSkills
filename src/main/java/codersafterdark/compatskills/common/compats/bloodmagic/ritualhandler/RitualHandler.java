package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.event.RitualEvent.RitualActivatedEvent;
import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import java.util.stream.Collectors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RitualHandler {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void ritualEvent(RitualActivatedEvent event) {
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLocks(Ritual.class, event.getRitual());
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.bloodmagic.ritual");
            String reqs = requirementHolder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
            player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
        }
    }
}