package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockKey;
import codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks.ToolTypeLockKey;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TinkerLockHandler {
    @SubscribeEvent
    public void onModifierAttached(TinkerCraftingEvent.ToolModifyEvent event) {
        PlayerData data = PlayerDataHandler.get(event.getPlayer());
        String canceledMessage = getCanceledMessage(data, getModifierRequirement(event.getModifiers()), I18n.format("compatskills.tconstruct.modifierError"));
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    @SubscribeEvent
    public void onCraftingMaterial(TinkerCraftingEvent.ToolPartCraftingEvent event) {
        PlayerData data = PlayerDataHandler.get(event.getPlayer());
        List<RequirementHolder> holders = getMaterialRequirements(Collections.singletonList(event.getItemStack()));
        String canceledMessage = getCanceledMessage(data, holders, I18n.format("compatskills.tconstruct.materialError"));
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    @SubscribeEvent
    public void onPartReplaced(TinkerCraftingEvent.ToolPartReplaceEvent event) {
        PlayerData data = PlayerDataHandler.get(event.getPlayer());
        String canceledMessage = getCanceledMessage(data, getMaterialRequirements(event.getToolParts()), I18n.format("compatskills.tconstruct.materialError"));
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    @SubscribeEvent
    public void onToolCrafted(TinkerCraftingEvent.ToolCraftingEvent event) {
        PlayerData data = PlayerDataHandler.get(event.getPlayer());
        if (data == null) {
            //TODO this event is the one that actually can have data be null
            return;
        }

        List<RequirementHolder> holders = new ArrayList<>();

        RequirementHolder toolHolder = LevelLockHandler.getLockByKey(new ToolTypeLockKey(event.getItemStack().getItem()));
        if (!toolHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
            holders.add(toolHolder);
        }

        holders.addAll(getMaterialRequirements(event.getToolParts()));

        String canceledMessage = getCanceledMessage(data, holders, I18n.format("compatskills.tconstruct.toolTypeError"));
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    private List<RequirementHolder> getMaterialRequirements(List<ItemStack> itemstacks) {
        List<RequirementHolder> holders = new ArrayList<>();
        for (ItemStack stack : itemstacks) {
            Material material = TinkerUtil.getMaterialFromStack(stack);
            RequirementHolder materialHolder = LevelLockHandler.getLockByKey(new MaterialLockKey(material));
            if (!materialHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
                holders.add(materialHolder);
            }
            RequirementHolder holder = getModifierRequirement(material.getDefaultTraits());
            if (!holder.equals(LevelLockHandler.EMPTY_LOCK)) {
                holders.add(holder);
            }
        }
        return holders;
    }

    private RequirementHolder getModifierRequirement(List<? extends IToolMod> modifiers) {
        IToolMod[] mods = new IToolMod[modifiers.size()];
        for (int i = 0; i < modifiers.size(); i++) {
            mods[i] = modifiers.get(i);
        }
        return LevelLockHandler.getLocks(IToolMod.class, mods);
    }

    //TODO: Potentially try to make an error message that says what lock types there are OR remove errorType and just have a generic tinker's error
    //This is because material locks could also be just because the material has a modifier that cannot be used
    //Or with full tool creation it could be any of the lock types
    private String getCanceledMessage(PlayerData data, List<RequirementHolder> holders, String errorType) {
        return holders.isEmpty() ? null : getCanceledMessage(data, new RequirementHolder(holders.toArray(new RequirementHolder[0])), errorType);
    }

    private String getCanceledMessage(PlayerData data, RequirementHolder holder, String errorType) {
        if (data == null || holder.equals(LevelLockHandler.EMPTY_LOCK) || data.matchStats(holder)) {
            return null;
        }
        StringBuilder reqs = new StringBuilder(errorType);
        reqs.append("\n\nRequirements:");
        for (Requirement req : holder.getRequirements()) {
            reqs.append('\n').append(req.getToolTip(data));
        }
        return reqs.toString();
    }
}