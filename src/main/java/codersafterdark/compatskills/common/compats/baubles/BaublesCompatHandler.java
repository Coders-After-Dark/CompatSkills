package codersafterdark.compatskills.common.compats.baubles;

import net.minecraftforge.common.MinecraftForge;

public class BaublesCompatHandler {
    public static void setup() {
        MinecraftForge.EVENT_BUS.register(new BaublesTickHandler());
    }
}