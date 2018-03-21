package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.common.compats.magneticraft.handlers.MagMultiBlockHandler;
import codersafterdark.compatskills.common.compats.utils.MultiBlockCommand;
import codersafterdark.compatskills.common.compats.utils.MultiBlockGate;
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
        CTChatCommand.registerCommand(new MultiBlockCommand("magMultiBlock") {
            @Override
            public List<String> getMultiBlockNames() {
                return new ArrayList<>(MagneticraftApi.getMultiblockManager().getRegisteredMultiblocks().keySet());
            }
        });
    }

    public static void addMultiBlockGate(MultiBlockGate multiBlockGate) {
        magMultiBlockHandler.addMultiBlockGate(multiBlockGate);
    }
}
