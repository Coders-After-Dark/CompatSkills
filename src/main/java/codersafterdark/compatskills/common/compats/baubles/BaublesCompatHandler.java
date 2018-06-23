package codersafterdark.compatskills.common.compats.baubles;

import codersafterdark.compatskills.utils.CompatModuleBase;
import net.minecraftforge.common.MinecraftForge;

public class BaublesCompatHandler extends CompatModuleBase {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new BaublesTickHandler());
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }
}