package codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.stream.Collectors;

public class EntityDamageEventHandler {
    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        if (event.isCanceled() || !(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        if (!ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new EntityDamageKey(event.getEntity()));
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            if (player.getEntityWorld().isRemote) {
                TextComponentTranslation error = new TextComponentTranslation("compatskills.entity.entityDamageError");
                TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
                String reqString = requirementHolder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
                ITextComponent textComponent = new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
                player.sendStatusMessage(textComponent, false);
            }
        }
    }
}