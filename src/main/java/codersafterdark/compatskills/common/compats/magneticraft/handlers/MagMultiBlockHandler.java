package codersafterdark.compatskills.common.compats.magneticraft.handlers;

import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.requirement.Requirement;
import com.cout970.magneticraft.api.multiblock.IMultiblock;
import com.cout970.magneticraft.api.multiblock.MultiBlockEvent;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class MagMultiBlockHandler {
    private Map<String, MultiBlockGate> multiBlockGates;

    public MagMultiBlockHandler() {
        multiBlockGates = Maps.newHashMap();
    }

    public void addMultiBlockGate(MultiBlockGate multiBlockGate) {
        multiBlockGates.put(multiBlockGate.getMultiBlockName(), multiBlockGate);
    }

    @SubscribeEvent
    public void multiBlockForm(MultiBlockEvent.CheckIntegrity event) {
        IMultiblock multiblock = event.getMultiblock();
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        String name = multiblock.getMultiblockName();

        if (multiBlockGates.containsKey(name)) {
            MultiBlockGate gate = multiBlockGates.get(name);
            if (!data.matchStats(gate.getRequirementHolder())) {
                String error = gate.getFailureMessage();
                List<Requirement> requirements = gate.getRequirementHolder().getRequirements();
                TextComponentString string = new TextComponentString(error + ":");
                for (Requirement requirement : requirements) {
                    string.appendText(requirement.getToolTip(data));
                }
                event.getIntegrityErrors().add(string);
            }
        }
    }
}