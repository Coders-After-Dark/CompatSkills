package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.common.compats.thaumcraft.keys.KnowledgeKey;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEvent;

import java.util.stream.Collectors;

public class KnowledgeHandler {
    @SubscribeEvent
    public void onKnowledgeEvent(ResearchEvent.Knowledge event) {
        IPlayerKnowledge.EnumKnowledgeType knowledgeType = event.getType();
        ResearchCategory category = event.getCategory();
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder holder = LevelLockHandler.getLockByKey(new KnowledgeKey(knowledgeType, category));
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.thaumcraft.knowledgeError");
            String reqs = holder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
            player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
        }
    }
}
