package codersafterdark.compatskills.common;

import codersafterdark.compatskills.common.compats.immersiveengineering.IECompatHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("immersiveengineering")) {
            IECompatHandler.setup();
        }
    }

    public void Init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStart(FMLServerStartingEvent event) {

    }
}
