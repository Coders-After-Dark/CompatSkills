package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.MaterialDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.ModifierDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks.ToolTypeLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

public class TinkersCompatHandler {
    private static MaterialLockHandler materialLockHandler;
    private static ModifierLockHandler modifierLockHandler;
    private static ToolTypeLockHandler toolTypeLockHandler;

    public static void setup() {
        materialLockHandler = new MaterialLockHandler();
        MinecraftForge.EVENT_BUS.register(materialLockHandler);
        modifierLockHandler = new ModifierLockHandler();
        MinecraftForge.EVENT_BUS.register(modifierLockHandler);
        toolTypeLockHandler = new ToolTypeLockHandler();
        MinecraftForge.EVENT_BUS.register(toolTypeLockHandler);
    }

    public static void setupServerStart() {
        CTChatCommand.registerCommand(new MaterialDumpCommand());
        CTChatCommand.registerCommand(new ModifierDumpCommand());
    }
}
