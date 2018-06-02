package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EntityMountEventHandler {
    @SubscribeEvent
    public void onMount(EntityMountEvent event) {
        if (event.isCanceled() || event.isDismounting() || !(event.getEntityMounting() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntityMounting();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new EntityMountKey(event.getEntityBeingMounted()));
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            List<Requirement> requirements = requirementHolder.getRequirements();
            TextComponentTranslation error = new TextComponentTranslation("compatskills.entity.entityMountError");
            TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
            StringBuilder reqString = new StringBuilder();
            for (Requirement requirement : requirements) {
                reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
            }
            ITextComponent textComponent = new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}