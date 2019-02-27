package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners.LevelHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners.LockHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners.UnlockHandler;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SkillChangeHandler {
    private static Map<SkillChange, Consumer<EntityPlayer>> skillChangeMap = new HashMap<>();
    private static boolean level, unlock, lock;

    public static void addSkillEvent(SkillChange change, String[] commands) {
        registerChange(change);
        skillChangeMap.put(change, player -> {
            MinecraftServer server = player.getServer();
            if (server != null) {
                ICommandManager commandManager = server.getCommandManager();
                for (String command : commands) {
                    commandManager.executeCommand(server, command);
                }
            }
        });
    }

    public static void addSkillEvent(SkillChange change, IChangeHandler handler) {
        registerChange(change);
        skillChangeMap.put(change, player -> handler.handleChange(CraftTweakerMC.getIPlayer(player)));
    }

    private static void registerChange(SkillChange change) {
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
    }

    //TODO: Should the listeners only be registered if it is server side instead of only acting on them if they are server side
    public static void handleCommands(SkillChange change, EntityPlayer player) {
        if (player == null || player.world.isRemote || player.getServer() == null) {
            return;
        }
        Consumer<EntityPlayer> handler = skillChangeMap.get(change);
        if (handler != null) {
            handler.accept(player);
        }
    }
}