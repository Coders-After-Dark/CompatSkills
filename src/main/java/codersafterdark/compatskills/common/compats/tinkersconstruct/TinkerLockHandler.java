package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks.ToolTypeLockKey;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TinkerLockHandler {
    //TODO: Potentially try to make an error message that says what lock types there are OR remove errorType and just have a generic tinker's error
    //This is because material locks could also be just because the material has a modifier that cannot be used
    //Or with full tool creation it could be any of the lock types
    public static String getCanceledMessage(PlayerData data, List<RequirementHolder> holders, String errorType) {
        return holders.isEmpty() ? null : getCanceledMessage(data, new RequirementHolder(holders.toArray(new RequirementHolder[0])), errorType);
    }

    public static String getCanceledMessage(PlayerData data, RequirementHolder holder, String errorType) {
        if (data == null || holder.equals(LevelLockHandler.EMPTY_LOCK) || data.matchStats(holder)) {
            return null;
        }
        StringBuilder reqs = new StringBuilder(errorType);
        reqs.append("\n\n").append(new TextComponentTranslation("compatskills.misc.requirements").getUnformattedComponentText());
        holder.getRequirements().forEach(req -> reqs.append('\n').append(req.getToolTip(data)));
        return reqs.toString();
    }

    @SubscribeEvent
    public void onCraftingMaterial(TinkerCraftingEvent.ToolPartCraftingEvent event) {
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        List<RequirementHolder> holders = getMaterialRequirements(Collections.singletonList(event.getItemStack()));
        String canceledMessage = getCanceledMessage(data, holders, new TextComponentTranslation("compatskills.error.tconstruct.material").getUnformattedComponentText());
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    @SubscribeEvent
    public void onPartReplaced(TinkerCraftingEvent.ToolPartReplaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        String canceledMessage = getCanceledMessage(data, getMaterialRequirements(event.getToolParts()), new TextComponentTranslation("compatskills.error.tconstruct.material").getUnformattedComponentText());
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    @SubscribeEvent
    public void onToolCrafted(TinkerCraftingEvent.ToolCraftingEvent event) {
        EntityPlayer player = event.getPlayer();
        //TODO: At some point make this use Utils.skipPlayer
        //TODO Cont: Below code already has handling for if the player is null, which maybe should stay
        if (!ConfigHandler.enforceOnCreative && player != null && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            event.setCanceled(true);//Support for canceling if old tinkers so that they cant create dupes
            return;
        }

        List<RequirementHolder> holders = new ArrayList<>();

        RequirementHolder toolHolder = LevelLockHandler.getLockByKey(new ToolTypeLockKey(event.getItemStack().getItem()));
        if (!toolHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
            holders.add(toolHolder);
        }

        holders.addAll(getMaterialRequirements(event.getToolParts()));

        String canceledMessage = getCanceledMessage(data, holders, new TextComponentTranslation("compatskills.error.tconstruct.tool_type").getUnformattedComponentText());
        if (canceledMessage != null) {
            event.setCanceled(canceledMessage);
        }
    }

    private List<RequirementHolder> getMaterialRequirements(List<ItemStack> itemstacks) {
        return itemstacks.stream().map(stack -> LevelLockHandler.getLocks(Material.class, TinkerUtil.getMaterialFromStack(stack))).filter(materialHolder ->
                !materialHolder.equals(LevelLockHandler.EMPTY_LOCK)).collect(Collectors.toList());
    }
}