package codersafterdark.compatskills.common.compats.magneticraft.handlers;

import codersafterdark.compatskills.common.compats.utils.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import com.cout970.magneticraft.api.multiblock.MultiBlockEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class MagMultiBlockHandler {
    private Map<String, MultiBlockGate> multiBlockGates;

    public void addMultiBlockGate(MultiBlockGate multiBlockGate) {
        multiBlockGates.put(multiBlockGate.getMultiBlockName(), multiBlockGate);
    }

//    @SubscribeEvent
//    public void multiBlockForm(MultiBlockEvent.CheckIntegrity event) {
//
//        EntityPlayer player = event.getPlayer();
//        PlayerData data = PlayerDataHandler.get(player);
//        if (multiBlockGates.containsKey()) {
//            MultiBlockGate gate = multiBlockGates.get() {
//                if (!data.matchStats(gate.getRequirementHolder())) {
//                    ITextComponent textComponent = new TextComponentString(gate.getFailureMessage());
//                    String requirements = gate.getRequirementHolder().getRequirements().toString();
//                    ITextComponent component = new TextComponentString(textComponent.toString() + requirements);
//                    event.getIntegrityErrors().add(component);
//                }
//            }
//        }
//    }
}