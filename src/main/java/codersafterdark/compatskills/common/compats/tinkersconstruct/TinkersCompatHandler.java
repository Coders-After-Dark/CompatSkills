package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.MaterialDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.ModifierDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockKey;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockKey;
import codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks.ToolTypeLockHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class TinkersCompatHandler {
    private static MaterialLockHandler materialLockHandler;
    private static ModifierLockHandler modifierLockHandler;
    private static ToolTypeLockHandler toolTypeLockHandler;

    public static void setup() {
        LevelLockHandler.registerLockKey(Material.class, MaterialLockKey.class);
        LevelLockHandler.registerLockKey(IModifier.class, ModifierLockKey.class);

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
