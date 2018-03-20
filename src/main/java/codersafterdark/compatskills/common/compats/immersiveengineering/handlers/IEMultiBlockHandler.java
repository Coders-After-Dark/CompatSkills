package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.api.MultiblockHandler.MultiblockFormEvent;
import codersafterdark.compatskills.common.compats.immersiveengineering.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class IEMultiBlockHandler {
    private Map<String, MultiBlockGate> multiBlockGates;

    public void addMultiBlockGate(MultiBlockGate multiBlockGate){
        multiBlockGates.put(multiBlockGate.getMultiBlockName(), multiBlockGate);
    }

    @SubscribeEvent
    public void multiBlockForm(MultiblockFormEvent event){
        IMultiblock multiblock = event.getMultiblock();
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        if (multiBlockGates.containsKey(multiblock.getUniqueName())){
            MultiBlockGate gate =  multiBlockGates.get(multiblock.getUniqueName());
            if (!data.matchStats(gate.getRequirementHolder())){
                event.setCanceled(true);
                if (player.getEntityWorld().isRemote){
                    player.sendStatusMessage(new TextComponentString(gate.getFailureMessage()), false);
                }
            }
        }
    }
}
