package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.MaterialDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.ModifierDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockKey;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.modifiers.IToolMod;

public class TinkersCompatHandler {
    private static TinkerLockHandler tinkerLockHandler;

    public static void setup() {
        LevelLockHandler.registerLockKey(IToolMod.class, ModifierLockKey.class);
        MinecraftForge.EVENT_BUS.register(tinkerLockHandler = new TinkerLockHandler());
    }

    public static void setupServerStart() {
        CTChatCommand.registerCommand(new MaterialDumpCommand());
        CTChatCommand.registerCommand(new ModifierDumpCommand());
    }
}
