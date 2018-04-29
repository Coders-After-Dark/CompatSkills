package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.common.compats.magneticraft.handlers.MagMultiBlockHandler;
import codersafterdark.compatskills.utils.multiblock.MultiBlockCommand;
import com.cout970.magneticraft.api.MagneticraftApi;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class MagCompatHandler {
    private static MagMultiBlockHandler magMultiBlockHandler;

    public static void setup() {
        magMultiBlockHandler = new MagMultiBlockHandler();
        MinecraftForge.EVENT_BUS.register(magMultiBlockHandler);
        CTChatCommand.registerCommand(new MultiBlockCommand("mag") {
            @Override
            public List<String> getMultiBlockNames() {
                return new ArrayList<>(MagneticraftApi.getMultiblockManager().getRegisteredMultiblocks().keySet());
            }
        });
    }
}
