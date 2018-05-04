package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.RitualHandler;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCostLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCrystalLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualNameLockKey;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class BMCompatHandler {
    private static BindHandler bindHandler;
    private static RitualHandler ritualHandler;

    public static void setup() {
        LevelLockHandler.registerLockKey(Ritual.class, RitualNameLockKey.class, RitualCrystalLockKey.class, RitualCostLockKey.class);

        bindHandler = new BindHandler();
        MinecraftForge.EVENT_BUS.register(bindHandler);
        ritualHandler = new RitualHandler();
        MinecraftForge.EVENT_BUS.register(ritualHandler);

        CTChatCommand.registerCommand(new RitualCommand() {
            @Override
            public List<String> getRitualNames() {
                return new ArrayList<>(RitualRegistry.getIds());
            }
        });
    }
}
