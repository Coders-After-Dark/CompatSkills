package codersafterdark.compatskills.utils;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class CompatSkillsConfig {
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @Config(modid = CompatSkillConstants.MOD_ID)
    public static class StargazerConfigs {
        public static Hwyla hwyla;
        public static TOP top;

        public static class Hwyla {
            @Comment("Should Hwyla Requirements Support require Shifting?")
            public static boolean HwylaShifting;
        }

        public static class TOP {
            @Comment("Should TOP Requirements Support require Shifting?")
            public static boolean TOPShifting;
        }
    }
}