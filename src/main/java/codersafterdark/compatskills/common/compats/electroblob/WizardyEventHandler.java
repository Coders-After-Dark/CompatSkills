package codersafterdark.compatskills.common.compats.electroblob;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import electroblob.wizardry.event.SpellBindEvent;
import electroblob.wizardry.inventory.ContainerArcaneWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WizardyEventHandler {

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void onSpellBind(SpellBindEvent event) {
        if (event.isCanceled() || !(event.getContainer() instanceof ContainerArcaneWorkbench)) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        ContainerArcaneWorkbench workbench = (ContainerArcaneWorkbench) event.getContainer();
        if (testSlot(data, player, workbench.getSlot(ContainerArcaneWorkbench.CRYSTAL_SLOT), "compatskills.error.wizardry.crystal")) {
            event.setCanceled(true);
        } else if (testSlot(data, player, workbench.getSlot(ContainerArcaneWorkbench.CENTRE_SLOT), "compatskills.error.wizardry.center")) {
            event.setCanceled(true);
        } else if (testSlot(data, player, workbench.getSlot(ContainerArcaneWorkbench.UPGRADE_SLOT), "compatskills.error.wizardry.upgrade")) {
            event.setCanceled(true);
        } else {
            for (int i = 0; i < 8; i++) {
                Slot spellSlot = workbench.getSlot(i);
                //Only check the spell slot if it is "enabled"
                if (spellSlot.xPos != -999 && spellSlot.yPos != -999 && testSlot(data, player, spellSlot, "compatskills.error.wizardry.spell")) {
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

    private boolean testSlot(PlayerData data, EntityPlayer player, Slot slot, String translationKeyError) {
        ItemStack stack = slot.getStack();
        if (stack.isEmpty()) {
            return false;
        }
        RequirementHolder requirementHolder = LevelLockHandler.getSkillLock(stack);
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            TextComponentTranslation error = new TextComponentTranslation(translationKeyError);
            player.sendStatusMessage(Utils.getError(requirementHolder, data, error), false);
            return true;
        }
        return false;
    }
}