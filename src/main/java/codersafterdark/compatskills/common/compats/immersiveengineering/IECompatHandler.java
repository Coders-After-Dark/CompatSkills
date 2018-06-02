package codersafterdark.compatskills.common.compats.immersiveengineering;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import codersafterdark.compatskills.utils.multiblock.MultiBlockCommand;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.stream.Collectors;

public class IECompatHandler {
    public static void setup() {
        MinecraftForge.EVENT_BUS.register(new IEMultiBlockHandler());
        CTChatCommand.registerCommand(new MultiBlockCommand("ie") {
            @Override
            public List<String> getMultiBlockNames() {
                return MultiblockHandler.getMultiblocks().stream().map(MultiblockHandler.IMultiblock::getUniqueName).collect(Collectors.toList());
            }
        });
    }
}