package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.compatskills.common.compats.minecraft.entity.EntityLockKey;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EntityMountEventHandler {
    @SubscribeEvent
    public void onMount(EntityMountEvent event){
        EntityPlayer player = null;
        PlayerData data = null;
        RequirementHolder requirementHolder = null;
        Entity entity = event.getEntityBeingMounted();
        if (event.getEntityMounting() instanceof EntityPlayer){
            player = (EntityPlayer) event.getEntityMounting();
            data = PlayerDataHandler.get(player);
        }
        if (player != null && data != null){
            requirementHolder = LevelLockHandler.getLockByKey(new EntityLockKey(entity));
        }
        if (requirementHolder != null && requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)){
            event.setCanceled(true);
            String error = I18n.format("compatskills.entity.entityMountError");
            List<Requirement> requirements = requirementHolder.getRequirements();
            StringBuilder reqString = new StringBuilder(I18n.format("compatskills.misc.Requirements"));
            for (Requirement requirement : requirements) {
                reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
            }
            ITextComponent textComponent = new TextComponentString(error + ' ' + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}
