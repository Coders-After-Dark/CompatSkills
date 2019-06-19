package codersafterdark.compatskills;

import codersafterdark.compatskills.common.CommonProxy;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

@Mod(modid = CompatSkillConstants.MOD_ID, name = CompatSkillConstants.MOD_NAME, version = CompatSkillConstants.VERSION, dependencies = CompatSkillConstants.DEPENDENCIES, acceptedMinecraftVersions = CompatSkillConstants.MCVER)
public class CompatSkills {
    public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

    @SidedProxy(serverSide = CompatSkillConstants.PROXY_COMMON, clientSide = CompatSkillConstants.PROXY_CLIENT)
    public static CommonProxy proxy;

    public static Logger logger;

    public static boolean craftweakerLoaded;

    //Ugly way of doing it, but it kept crashing when trying to parse the signature
    public static void registerCommand(Object command) {
        if (command instanceof crafttweaker.mc1120.commands.CraftTweakerCommand) {
            CTChatCommand.registerCommand((crafttweaker.mc1120.commands.CraftTweakerCommand) command);
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        craftweakerLoaded = Loader.isModLoaded("crafttweaker");
        CompatModuleBase.doModulesPreInit();
        if (event.getSide().isClient()) {
            CompatModuleBase.doModulesPreInitClient();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.Init(event);
        CompatModuleBase.doModulesInit();
        if (event.getSide().isClient()) {
            CompatModuleBase.doModulesInitClient();
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        CompatModuleBase.doModulesPostInit();
        if (event.getSide().isClient()) {
            CompatModuleBase.doModulesPostInitClient();
        }
        if (craftweakerLoaded) {
            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        }
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        proxy.serverStart(event);
        CompatModuleBase.doModulesLoadComplete();
    }
}