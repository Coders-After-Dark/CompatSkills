package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.api.MultiblockHandler.MultiblockFormEvent;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.utils.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.Map;

public class IEMultiBlockHandler {
    private Map<String, MultiBlockGate> multiBlockGates;

    public IEMultiBlockHandler(){
        multiBlockGates = Maps.newHashMap();
    }

    public void addMultiBlockGate(MultiBlockGate multiBlockGate) {
        multiBlockGates.put(multiBlockGate.getMultiBlockName(), multiBlockGate);
    }

    @SubscribeEvent
    public void multiBlockForm(MultiblockFormEvent event) {
        IMultiblock multiblock = event.getMultiblock();
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        String name = multiblock.getUniqueName();
        if (multiBlockGates.containsKey(name)) {
            MultiBlockGate gate = multiBlockGates.get(name);
            if (!data.matchStats(gate.getRequirementHolder())) {
                event.setCanceled(true);
                CompatSkills.logger.log(Level.INFO, player.getEntityWorld().isRemote);
                String fail = gate.getFailureMessage();
                if (player.getEntityWorld().isRemote) {
                    player.sendStatusMessage(new TextComponentString(fail), true);
                }
            }
        }
    }
}
