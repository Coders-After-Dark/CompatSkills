package codersafterdark.compatskills.utils;

import com.google.common.collect.Maps;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.Map;

public class CompatSkillsConfig {
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @Config(modid = CompatSkillConstants.MOD_ID, type = Config.Type.INSTANCE, name = CompatSkillConstants.MOD_ID)
    public static class Configs {
        public static Modules modules;
        public static Hwyla hwyla;
        public static TOP top;
        public static Minecraft minecraft;

        public static class Modules {
            @Config.Comment({"A list of all mods that CompatSkills has integrated compatability with.", "Setting any of these to false disables the respective compat:"})
            public static Map<String, Boolean> compat = Maps.newHashMap(Maps.toMap(CompatModuleBase.moduleClasses.keySet(), (k) -> Boolean.TRUE));
        }

        public static class Hwyla {
            @Comment("Should Hwyla Requirements Support require Shifting?")
            public static boolean HwylaShifting;
        }

        public static class TOP {
            @Comment("Should TOP Requirements Support require Shifting?")
            public static boolean TOPShifting;
        }

        public static class Minecraft {
            @Comment("Should locked Block Drops post their requirements?")
            public static boolean BlockDropsError;
        }
    }
}