package codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;

public class MaterialLockHandler {
    @SubscribeEvent
    public void onCraftingMaterial(TinkerCraftingEvent.ToolPartCraftingEvent event) {
        Material material = TinkerUtil.getMaterialFromStack(event.getItemStack());
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder holder = LevelLockHandler.getLocks(Material.class, material);
        if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            List<Requirement> requirements = holder.getRequirements();
            StringBuilder reqs = new StringBuilder(I18n.format("compatskills.tconstruct.materialError") + "\n" + "With Requirements: ");
            for (Requirement req : requirements) {
                reqs.append("\n").append(req.getToolTip(data));
            }
            event.setCanceled(reqs.toString());
        }
    }

    @SubscribeEvent
    public void onToolCrafted(TinkerCraftingEvent.ToolCraftingEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        for (ItemStack stack : event.getToolParts()) {
            Material material = TinkerUtil.getMaterialFromStack(stack);
            RequirementHolder holder = LevelLockHandler.getLocks(Material.class, material);
            if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                List<Requirement> requirements = holder.getRequirements();
                StringBuilder reqs = new StringBuilder(I18n.format("compatskills.tconstruct.materialError") + "\n" + "With Requirements: ");
                for (Requirement req : requirements) {
                    reqs.append("\n").append(req.getToolTip(data));
                }
                event.setCanceled(reqs.toString());
            }
        }
    }

    @SubscribeEvent
    public void onPartReplaced(TinkerCraftingEvent.ToolPartReplaceEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        for (ItemStack stack : event.getToolParts()) {
            Material material = TinkerUtil.getMaterialFromStack(stack);
            RequirementHolder holder = LevelLockHandler.getLocks(Material.class, material);
            if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                event.setCanceled(true);
                List<Requirement> requirements = holder.getRequirements();
                StringBuilder reqs = new StringBuilder(I18n.format("compatskills.tconstruct.materialError") + "\n" + "With Requirements: ");
                for (Requirement req : requirements) {
                    reqs.append("\n").append(req.getToolTip(data));
                }
                event.setCanceled(reqs.toString());
            }
        }
    }
}
