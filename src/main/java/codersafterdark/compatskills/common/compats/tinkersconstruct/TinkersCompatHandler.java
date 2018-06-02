package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.MaterialDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.commands.ModifierDumpCommand;
import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockKey;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockKey;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IToolMod;

public class TinkersCompatHandler {
    public static void setup() {
        LevelLockHandler.registerLockKey(Material.class, MaterialLockKey.class);
        LevelLockHandler.registerLockKey(IToolMod.class, ModifierLockKey.class);
        MinecraftForge.EVENT_BUS.register(new TinkerLockHandler());
    }

    public static void setupServerStart() {
        CTChatCommand.registerCommand(new MaterialDumpCommand());
        CTChatCommand.registerCommand(new ModifierDumpCommand());
    }
}