package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.api.MultiblockHandler.MultiblockFormEvent;
import codersafterdark.compatskills.common.compats.utils.MultiBlockGate;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class IEMultiBlockHandler {
    private Map<String, MultiBlockGate> multiBlockGates;

    public IEMultiBlockHandler() {
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
                String fail = gate.getFailureMessage();
                if (player.getEntityWorld().isRemote) {
                    player.sendStatusMessage(new TextComponentString(fail), true);
                }
            }
        }
    }

//    @SubscribeEvent
//    public void multiBlockBlockCatch(PlayerInteractEvent.RightClickBlock event){
//        EntityPlayer player = event.getEntityPlayer();
//        PlayerData data = PlayerDataHandler.get(player);
//        BlockPos pos = event.getPos();
//        World world = event.getWorld();
//        Block block = world.getBlockState(pos).getBlock();
//
//    }
}
