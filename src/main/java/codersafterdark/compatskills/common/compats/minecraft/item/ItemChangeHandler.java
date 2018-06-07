package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.HarvestLevelRequirement;
import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemChangeHandler {
    @SubscribeEvent
    public void heldItemChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof EntityPlayer && (event.getSlot().equals(EntityEquipmentSlot.MAINHAND) || event.getSlot().equals(EntityEquipmentSlot.OFFHAND))) {
            RequirementCache.invalidateCache(event.getEntity().getUniqueID(), ItemRequirement.class, OreDictRequirement.class, HarvestLevelRequirement.class);
        }
    }
}