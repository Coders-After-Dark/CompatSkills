package codersafterdark.compatskills.common.compats.cyberware;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import flaxbeard.cyberware.api.CyberwareSurgeryEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;

public class CyberwareEventHandler {

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void onSurgeryAttempt(CyberwareSurgeryEvent.Pre event) {
        if (event.isCanceled() || !(event.getEntity() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntity();
        if (Utils.skipPlayer(player)) {
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        List<ItemStack> targetCyberwares = getStacksForHandler(event.getTargetCyberwares());
        for (ItemStack stack : targetCyberwares) {
            RequirementHolder requirementHolder = LevelLockHandler.getSkillLock(stack);
            if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
                event.setCanceled(true);
                TextComponentTranslation error = new TextComponentTranslation("compatskills.error.cyberware.install");
                player.sendStatusMessage(Utils.getError(requirementHolder, data, error), false);
                //Don't bother continuing to check the other items
                return;
            }
        }
    }

    private List<ItemStack> getStacksForHandler(ItemStackHandler handler) {
        return IntStream.range(0, handler.getSlots()).mapToObj(handler::getStackInSlot).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
    }
}