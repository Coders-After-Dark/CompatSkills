package codersafterdark.compatskills.common.compats.baubles;

import net.minecraftforge.common.MinecraftForge;

public class BaublesCompatHandler {
    private static BaublesTickHandler baublesTickHandler;

    public static void setup() {
        baublesTickHandler = new BaublesTickHandler();
        MinecraftForge.EVENT_BUS.register(baublesTickHandler);
    }
}
