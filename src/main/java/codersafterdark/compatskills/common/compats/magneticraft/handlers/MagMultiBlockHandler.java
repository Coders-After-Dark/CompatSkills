package codersafterdark.compatskills.common.compats.magneticraft.handlers;

import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import com.cout970.magneticraft.api.multiblock.IMultiblock;
import com.cout970.magneticraft.api.multiblock.MultiBlockEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class MagMultiBlockHandler {
    @SubscribeEvent
    public void multiBlockForm(MultiBlockEvent.CheckIntegrity event) {
        IMultiblock multiblock = event.getMultiblock();
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        String name = multiblock.getMultiblockName();

        MultiBlockGate gate = new MultiBlockGate(name);

        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(gate);
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            List<Requirement> requirements = requirementHolder.getRequirements();
            TextComponentString string = new TextComponentString(MessageStorage.getFailureMessage(gate) + ':');
            for (Requirement requirement : requirements) {
                string.appendText(requirement.getToolTip(data));
            }
            event.getIntegrityErrors().add(string);
        }
    }
}