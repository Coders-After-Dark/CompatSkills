package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.compatskills.utils.multiblock.MultiBlockCommand;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import com.cout970.magneticraft.api.MagneticraftApi;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class MagCompatHandler extends CompatModuleBase {
    private static boolean registered;

    @Override
    public void preInit() {

    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void loadComplete() {
        CTChatCommand.registerCommand(new MultiBlockCommand("mag") {
            @Override
            public List<String> getMultiBlockNames() {
                return new ArrayList<>(MagneticraftApi.getMultiblockManager().getRegisteredMultiblocks().keySet());
            }
        });
    }

    public static void addMagLock(MultiBlockGate key, RequirementHolder holder) {
        if (!registered) {
            MinecraftForge.EVENT_BUS.register(new MagMultiBlockHandler());
            registered = true;
        }
        LevelLockHandler.addLockByKey(key, holder);
    }
}