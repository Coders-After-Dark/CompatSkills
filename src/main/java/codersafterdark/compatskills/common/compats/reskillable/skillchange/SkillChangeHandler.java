package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners.LevelHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners.LockHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners.UnlockHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public class SkillChangeHandler {
    private static Map<SkillChange, String[]> skillChangeMap = new HashMap<>();
    private static boolean level, unlock, lock;

    public static void addSkillEvent(SkillChange change, String[] commands) {
        //Only register needed listeners
        if (change instanceof SkillLevel) {
            if (!level) {
                MinecraftForge.EVENT_BUS.register(new LevelHandler());
                level = true;
            }
        } else if (change instanceof UnlockableUnlock) {
            if (!unlock) {
                MinecraftForge.EVENT_BUS.register(new UnlockHandler());
                unlock = true;
            }
        } else if (change instanceof UnlockableLock) {
            if (!lock) {
                MinecraftForge.EVENT_BUS.register(new LockHandler());
                lock = true;
            }
        }
        skillChangeMap.put(change, commands);
    }

    //TODO: Should the listeners only be registered if it is server side instead of only acting on them if they are server side
    public static void handleCommands(SkillChange change, EntityPlayer player) {
        if (player == null || player.world.isRemote || player.getServer() == null) {
            return;
        }
        ICommandManager commandManager = player.getServer().getCommandManager();
        String[] commands = skillChangeMap.get(change);
        if (commands != null) {
            for (String command : commands) {
                commandManager.executeCommand(player.getServer(), command);
            }
        }
    }
}