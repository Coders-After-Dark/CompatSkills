package codersafterdark.compatskills.common.compats.immersiveengineering;

import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.api.MultiblockHandler.MultiblockFormEvent;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import java.util.stream.Collectors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IEMultiBlockHandler {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void multiBlockForm(MultiblockFormEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        IMultiblock multiblock = event.getMultiblock();
        PlayerData data = PlayerDataHandler.get(player);
        MultiBlockGate gate = new IEMultiBlockGate(multiblock.getUniqueName());
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(gate);
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            if (player.getEntityWorld().isRemote) {
                String reqs = requirementHolder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
                player.sendStatusMessage(new TextComponentString(MessageStorage.getFailureMessage(gate) + CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
            }
        }
    }
}