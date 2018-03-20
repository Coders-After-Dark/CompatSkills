package codersafterdark.compatskills.common.compats.immersiveengineering;

import codersafterdark.compatskills.common.compats.immersiveengineering.handlers.IEMultiBlockHandler;
import net.minecraftforge.common.MinecraftForge;

public class IECompatHandler {
    private static IEMultiBlockHandler multiBlockHandler;

    public static void setup(){
        multiBlockHandler = new IEMultiBlockHandler();
        MinecraftForge.EVENT_BUS.register(multiBlockHandler);
    }

    public static void addMultiBlockGate(MultiBlockGate multiBlockGate){
        multiBlockHandler.addMultiBlockGate(multiBlockGate);
    }
}
