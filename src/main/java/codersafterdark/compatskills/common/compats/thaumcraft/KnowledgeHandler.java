package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.common.compats.thaumcraft.keys.KnowledgeKey;
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
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEvent;

public class KnowledgeHandler {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void onKnowledgeEvent(ResearchEvent.Knowledge event) {
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder holder = LevelLockHandler.getLockByKey(new KnowledgeKey(event.getCategory(), event.getType().getAbbreviation()));
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.thaumcraft.knowledge");
            String reqs = holder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
            player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + ' ' +
                    ResearchCategories.getCategoryName(event.getCategory().key) + " (" + event.getType() + ")." +
                    CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
        }
    }
}