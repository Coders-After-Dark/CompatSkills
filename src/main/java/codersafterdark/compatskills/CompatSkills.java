package codersafterdark.compatskills;

import codersafterdark.compatskills.common.CommonProxy;
import codersafterdark.compatskills.common.compats.immersiveengineering.IECompatHandler;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CompatSkillConstants.MOD_ID, name = CompatSkillConstants.MOD_NAME, version = CompatSkillConstants.VERSION, dependencies = CompatSkillConstants.DEPENDENCIES, acceptedMinecraftVersions = CompatSkillConstants.MCVER)
public class CompatSkills {
    @SidedProxy(serverSide = CompatSkillConstants.PROXY_COMMON, clientSide = CompatSkillConstants.PROXY_CLIENT)
    public static CommonProxy proxy;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        if (Loader.isModLoaded("immersiveengineering")) {
            IECompatHandler.setup();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.Init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        proxy.serverStart(event);
    }
}
