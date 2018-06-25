package codersafterdark.compatskills.common.compats.baubles;

import codersafterdark.compatskills.utils.CompatModuleBase;
import net.minecraftforge.common.MinecraftForge;

public class BaublesCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new BaublesTickHandler());
    }
}