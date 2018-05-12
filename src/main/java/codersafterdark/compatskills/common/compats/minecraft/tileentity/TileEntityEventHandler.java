package codersafterdark.compatskills.common.compats.minecraft.tileentity;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class TileEntityEventHandler {
    @SubscribeEvent
    public void onTileEntityInteract(RightClickBlock event) {
        checkEvent(event);
    }

    @SubscribeEvent
    public void onTileEntityInteract(LeftClickBlock event) {
        checkEvent(event);
    }

    private void checkEvent(PlayerInteractEvent event) {
        if (event.isCanceled()) {
            return;
        }
        TileEntity entity = event.getWorld().getTileEntity(event.getPos());
        if (entity != null) {
            ResourceLocation location = TileEntity.getKey(entity.getClass());
            if (location != null) {
                EntityPlayer player = event.getEntityPlayer();
                PlayerData data = PlayerDataHandler.get(player);
                RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new TileEntityLockKey(location.toString()));
                if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
                    event.setCanceled(true);
                    if (player.getEntityWorld().isRemote) {
                        List<Requirement> requirements = requirementHolder.getRequirements();
                        TextComponentTranslation error = new TextComponentTranslation("compatskills.tileentity.interactError");
                        TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.Requirements");
                        StringBuilder reqString = new StringBuilder();
                        for (Requirement requirement : requirements) {
                            reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
                        }
                        ITextComponent textComponent = new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
                        player.sendStatusMessage(textComponent, false);
                    }
                }
            }
        }
    }
}