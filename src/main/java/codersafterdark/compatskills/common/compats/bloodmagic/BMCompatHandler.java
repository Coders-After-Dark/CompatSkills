package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.core.registry.RitualRegistry;
import codersafterdark.compatskills.common.compats.bloodmagic.BindHandler.BindHandler;
import codersafterdark.compatskills.common.compats.bloodmagic.RitualHandler.RitualHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class BMCompatHandler {
    private static BindHandler bindHandler;
    private static RitualHandler ritualHandler;

    public static void setup(){
        bindHandler = new BindHandler();
        MinecraftForge.EVENT_BUS.register(bindHandler);
        ritualHandler = new RitualHandler();
        MinecraftForge.EVENT_BUS.register(ritualHandler);

        CTChatCommand.registerCommand(new RitualCommand() {
            @Override
            public List<String> getRitualNames() {
                return new ArrayList<>(RitualRegistry.getRegistry().keySet());
            }
        });
    }
}
