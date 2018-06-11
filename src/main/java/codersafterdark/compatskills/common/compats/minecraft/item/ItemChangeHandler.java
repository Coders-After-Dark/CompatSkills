package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.HarvestLevelRequirement;
import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemChangeHandler {
    @SubscribeEvent
    public void heldItemChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof EntityPlayer && (event.getSlot().equals(EntityEquipmentSlot.MAINHAND) || event.getSlot().equals(EntityEquipmentSlot.OFFHAND))) {
            //TODO should it check to see if the items are the same or if they just switched places? If either are true then there is no need to invalidate the cache
            RequirementCache.invalidateCache(event.getEntity().getUniqueID(), ItemRequirement.class, OreDictRequirement.class, HarvestLevelRequirement.class);
        }
    }

    @SubscribeEvent
    public void interactEvent(PlayerInteractEvent event) {
        //Invalidate ItemRequirements when interacting because it is possible that the durability changed for example
        RequirementCache.invalidateCache(event.getEntityPlayer().getUniqueID(), ItemRequirement.class);
        //TODO: keep track of the held and offhand item of the player as copies? Then it can see if they changed.
        //TODO Cont: Probably is not actually needed because of the performance improvements with the caching elsewhere
    }
}