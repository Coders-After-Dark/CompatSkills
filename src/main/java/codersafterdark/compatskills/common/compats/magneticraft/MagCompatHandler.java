package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.utils.multiblock.MultiBlockCommand;
import com.cout970.magneticraft.api.MagneticraftApi;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class MagCompatHandler {
    public static void setup() {
        MinecraftForge.EVENT_BUS.register(new MagMultiBlockHandler());
        CTChatCommand.registerCommand(new MultiBlockCommand("mag") {
            @Override
            public List<String> getMultiBlockNames() {
                return new ArrayList<>(MagneticraftApi.getMultiblockManager().getRegisteredMultiblocks().keySet());
            }
        });
    }
}