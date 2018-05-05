package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockKey;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockKey;
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
        List<RequirementHolder> holders = getModifierRequirements(data, event.getModifiers());
        if (!holders.isEmpty()) {
            event.setCanceled(getCanceledMessage(data, holders, I18n.format("compatskills.tconstruct.modifierError")));
        }
    }

    @SubscribeEvent
    public void onCraftingMaterial(TinkerCraftingEvent.ToolPartCraftingEvent event) {
        PlayerData data = PlayerDataHandler.get(event.getPlayer());
        List<RequirementHolder> holders = getMaterialRequirements(data, Collections.singletonList(event.getItemStack()));
        if (!holders.isEmpty()) {
            event.setCanceled(getCanceledMessage(data, holders, I18n.format("compatskills.tconstruct.materialError")));
        }
    }

    @SubscribeEvent
    public void onPartReplaced(TinkerCraftingEvent.ToolPartReplaceEvent event) {
        PlayerData data = PlayerDataHandler.get(event.getPlayer());
        List<RequirementHolder> holders = getMaterialRequirements(data, event.getToolParts());
        if (!holders.isEmpty()) {
            event.setCanceled(getCanceledMessage(data, holders, I18n.format("compatskills.tconstruct.materialError")));
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
        if (toolHolder != null && !toolHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(toolHolder)) {
            holders.add(toolHolder);
        }

        holders.addAll(getMaterialRequirements(data, event.getToolParts()));

        if (!holders.isEmpty()) {
            event.setCanceled(getCanceledMessage(data, holders, I18n.format("compatskills.tconstruct.toolTypeError")));
        }
    }

    private List<RequirementHolder> getMaterialRequirements(PlayerData data, List<ItemStack> itemstacks) {
        if (data == null) {
            return new ArrayList<>();
        }
        List<RequirementHolder> holders = new ArrayList<>();
        for (ItemStack stack : itemstacks) {
            Material material = TinkerUtil.getMaterialFromStack(stack);
            RequirementHolder materialHolder = LevelLockHandler.getLockByKey(new MaterialLockKey(material));
            if (materialHolder != null && !materialHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(materialHolder)) {
                holders.add(materialHolder);
            }
            holders.addAll(getModifierRequirements(data, material.getDefaultTraits()));
        }
        return holders;
    }

    private List<RequirementHolder> getModifierRequirements(PlayerData data, List<? extends IToolMod> modifiers) {
        if (data == null) {
            return new ArrayList<>();
        }
        List<RequirementHolder> holders = new ArrayList<>();
        for (IToolMod trait : modifiers) {
            RequirementHolder holder = LevelLockHandler.getLockByKey(new ModifierLockKey(trait));
            if (holder != null && !holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                holders.add(holder);
            }
        }
        return holders;
    }

    //TODO: Potentially try to make an error message that says what lock types there are OR remove errorType and just have a generic tinker's error
    //This is because material locks could also be just because the material has a modifier that cannot be used
    //Or with full tool creation it could be any of the lock types
    private String getCanceledMessage(PlayerData data, List<RequirementHolder> holders, String errorType) {
        if (data == null) {
            return null;
        }
        StringBuilder reqs = new StringBuilder(errorType);
        reqs.append("\n\nRequirements:");
        RequirementHolder holder = new RequirementHolder(holders.toArray(new RequirementHolder[0]));
        for (Requirement req : holder.getRequirements()) {
            reqs.append('\n').append(req.getToolTip(data));//Will this be null pointer?
        }
        return reqs.toString();
    }
}