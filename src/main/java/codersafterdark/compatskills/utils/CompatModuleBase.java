package codersafterdark.compatskills.utils;

import codersafterdark.compatskills.CompatSkills;
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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CompatModuleBase {
    static HashMap<String, Class<? extends CompatModuleBase>> moduleClasses = new HashMap<>();
    private static Set<CompatModuleBase> modules = new HashSet<>();
    private static boolean serverStartingDone;

    static {
        moduleClasses.put("baubles", BaublesCompatHandler.class);
        moduleClasses.put("bloodmagic", BMCompatHandler.class);
        moduleClasses.put("gamestages", GameStageCompatHandler.class);
        moduleClasses.put("immersiveengineering", IECompatHandler.class);
        moduleClasses.put("magneticraft", MagCompatHandler.class);
        moduleClasses.put("minecraft", MinecraftCompatHandler.class);
        moduleClasses.put("projecte", ProjectECompatHandler.class);
        moduleClasses.put("reskillable", ReskillableCompatHandler.class);
        moduleClasses.put("theoneprobe", TOPCompatHandler.class);
        moduleClasses.put("tconstruct", TinkersCompatHandler.class);
    }

    public static void doModulesPreInit() {
        for (Map.Entry<String, Class<? extends CompatModuleBase>> e : moduleClasses.entrySet()) {
            if (Loader.isModLoaded(e.getKey()) || e.getKey().equals("minecraft")) {
                try {
                    Boolean enabled = CompatSkillsConfig.Configs.Modules.compat.get(e.getKey());
                    if (enabled == null || !enabled) {
                        continue;
                    }
                    CompatModuleBase m = e.getValue().newInstance();
                    modules.add(m);
                    m.preInit();
                } catch (Exception exception) {
                    CompatSkills.logger.error("Compat module for " + e.getKey() + " could not be preInitialized. Report this!");
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void doModulesPreInitClient() {
        for (CompatModuleBase compat : modules) {
            try {
                compat.clientPreInit();
            } catch (Exception exception) {
                CompatSkills.logger.error("Client compat module for " + compat + " could not be preInitialized. Report this!");
            }
        }
    }

    public static void doModulesInit() {
        for (CompatModuleBase compat : modules) {
            try {
                compat.init();
            } catch (Exception exception) {
                CompatSkills.logger.error("Compat module for " + compat + " could not be initialized");
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void doModulesInitClient() {
        for (CompatModuleBase compat : modules) {
            try {
                compat.clientInit();
            } catch (Exception exception) {
                CompatSkills.logger.error("Client compat module for " + compat + " could not be initialized");
            }
        }
    }

    public static void doModulesPostInit() {
        for (CompatModuleBase compat : modules) {
            try {
                compat.postInit();
            } catch (Exception exception) {
                CompatSkills.logger.error("Compat module for " + compat + " could not be postInitialized");
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void doModulesPostInitClient() {
        for (CompatModuleBase compat : modules) {
            try {
                compat.clientPostInit();
            } catch (Exception exception) {
                CompatSkills.logger.error("Client compat module for " + compat + " could not be postInitialized");
            }
        }
    }

    public static void doModulesLoadComplete() {
        if (!serverStartingDone) {
            serverStartingDone = true;
            for (CompatModuleBase compat : modules) {
                try {
                    compat.loadComplete();
                } catch (Exception exception) {
                    CompatSkills.logger.error("Compat module for " + compat + " could not be initialized");
                    exception.printStackTrace();
                }
            }
        }
    }

    public abstract void preInit();

    public abstract void init();

    public abstract void postInit();

    public void loadComplete() {
    }

    @SideOnly(Side.CLIENT)
    public void clientPreInit() {
    }

    @SideOnly(Side.CLIENT)
    public void clientInit() {
    }

    @SideOnly(Side.CLIENT)
    public void clientPostInit() {
    }
}
