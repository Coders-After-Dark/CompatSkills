package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.materialLocks.MaterialLockHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierLocks.ModifierLockHandler;
import codersafterdark.compatskills.common.compats.tinkersconstruct.toolLocks.ToolTypeLockHandler;
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
}
