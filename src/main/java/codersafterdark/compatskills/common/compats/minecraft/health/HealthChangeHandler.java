package codersafterdark.compatskills.common.compats.minecraft.health;

import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HealthChangeHandler {
    //These are lowest priority because we only care if it actually was updated for invalidating the cache and these are cancelable

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHeal(LivingHealEvent event) {
        if (event.getAmount() != 0 && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            RequirementCache.invalidateCache(player, HealthRequirement.class, HeartRequirement.class);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDamaged(LivingDamageEvent event) {
        if (event.getAmount() != 0 && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            RequirementCache.invalidateCache(player, HealthRequirement.class, HeartRequirement.class);
        }
    }
}