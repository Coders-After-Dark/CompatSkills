package codersafterdark.compatskills;

import codersafterdark.compatskills.common.CommonProxy;
import codersafterdark.compatskills.common.compats.baubles.BaublesCompatHandler;
import codersafterdark.compatskills.common.compats.bloodmagic.BMCompatHandler;
import codersafterdark.compatskills.common.compats.gamestages.GameStageCompatHandler;
import codersafterdark.compatskills.common.compats.immersiveengineering.IECompatHandler;
import codersafterdark.compatskills.common.compats.magneticraft.MagCompatHandler;
import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.common.compats.projecte.ProjectECompatHandler;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.common.compats.theoneprobe.TOPCompatHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.TinkersCompatHandler;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
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
    public static boolean TINKERS_LOADED;

    @SidedProxy(serverSide = CompatSkillConstants.PROXY_COMMON, clientSide = CompatSkillConstants.PROXY_CLIENT)
    public static CommonProxy proxy;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        CompatModuleBase.doModulesPreInit();
        CompatModuleBase.doModulesPreInitClient();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.Init(event);
        CompatModuleBase.doModulesInit();
        CompatModuleBase.doModulesInitClient();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        CompatModuleBase.doModulesPostInit();
        CompatModuleBase.doModulesPostInitClient();
        if (Loader.isModLoaded("crafttweaker")) {
            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        }
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        proxy.serverStart(event);
        CompatModuleBase.doModulesLoadComplete();
    }
}