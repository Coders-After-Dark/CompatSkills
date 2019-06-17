package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.ToolHarvestLock;
import codersafterdark.compatskills.common.compats.minecraft.item.weapon.AttackDamageLockKey;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;

import java.util.*;

public class TinkerMCHandler {
    @SubscribeEvent
    public void onToolDamageModified(TinkerCraftingEvent.ToolModifyEvent event) {
        EntityPlayer player = event.getPlayer();

        if (Utils.skipPlayer(player)) {
            return;
        }

        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            event.setCanceled(true);
            return;
        }


        Multimap<String, AttributeModifier> attributeModifiers = event.getItemStack().getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
        Collection<AttributeModifier> damage = attributeModifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
        double toolDmg = damage.stream().findFirst().map(AttributeModifier::getAmount).orElse(0D);
        RequirementHolder holder = LevelLockHandler.getLockByFuzzyKey(new AttackDamageLockKey(toolDmg));
        if (holder.equals(LevelLockHandler.EMPTY_LOCK)) {
            return;
        }

        String canceledMessage = TinkerLockHandler.getCanceledMessage(data, holder, new TextComponentTranslation("compatskills.error.tconstruct.tool_damage").getUnformattedComponentText());
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    @SubscribeEvent
    public void onHarvestLevelModified(TinkerCraftingEvent.ToolModifyEvent event) {
        EntityPlayer player = event.getPlayer();

        if (Utils.skipPlayer(player)) {
            return;
        }

        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            event.setCanceled(true);
            return;
        }

        Map<String, Integer> typeLevels = new HashMap<>();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        int highestLevel = 0;
        List<RequirementHolder> holders = new ArrayList<>();


        for (String type : item.getToolClasses(stack)) {
            int level = item.getHarvestLevel(stack, type, null, null);
            if (level < 0) {
                return;
            }
            typeLevels.put(type, level);
            if (level > highestLevel) {
                highestLevel = level;
            }
        }

        for (Map.Entry<String, Integer> entry : typeLevels.entrySet()){
            holders.add(LevelLockHandler.getLockByFuzzyKey(new ToolHarvestLock(entry.getKey(), entry.getValue())));
        }

        holders.add(LevelLockHandler.getLockByFuzzyKey(new ToolHarvestLock(null, highestLevel)));

        String canceledMessage = TinkerLockHandler.getCanceledMessage(data, holders, new TextComponentTranslation("compatskills.error.tconstruct.tool_harvest_level").getUnformattedComponentText());
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }
}
