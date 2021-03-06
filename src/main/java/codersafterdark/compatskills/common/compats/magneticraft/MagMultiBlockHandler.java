package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import com.cout970.magneticraft.api.multiblock.IMultiblock;
import com.cout970.magneticraft.api.multiblock.MultiBlockEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MagMultiBlockHandler {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public void multiBlockForm(MultiBlockEvent.CheckIntegrity event) {
        //TODO: Magneticraft compat spams requirement achieved
        if (!event.getIntegrityErrors().isEmpty()) {//Already has issues
            return;
        }
        EntityPlayer player = event.getPlayer();
        if (Utils.skipPlayer(player)) {
            return;
        }
        IMultiblock multiblock = event.getMultiblock();
        PlayerData data = PlayerDataHandler.get(player);
        MultiBlockGate gate = new MagMultiBlockGate(multiblock.getMultiblockName());
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(gate);
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            TextComponentString string = new TextComponentString(MessageStorage.getFailureMessage(gate) + ':');
            requirementHolder.getRequirements().stream().map(requirement -> requirement.getToolTip(data)).forEach(string::appendText);
            event.getIntegrityErrors().add(string);
        }
    }
}