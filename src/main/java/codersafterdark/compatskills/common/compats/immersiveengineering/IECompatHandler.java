package codersafterdark.compatskills.common.compats.immersiveengineering;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import codersafterdark.compatskills.common.compats.immersiveengineering.handlers.IEMultiBlockHandler;
import codersafterdark.compatskills.utils.multiblock.MultiBlockCommand;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.stream.Collectors;

public class IECompatHandler {
    private static IEMultiBlockHandler multiBlockHandler;

    public static void setup() {
        multiBlockHandler = new IEMultiBlockHandler();
        MinecraftForge.EVENT_BUS.register(multiBlockHandler);
        CTChatCommand.registerCommand(new MultiBlockCommand("ie") {
            @Override
            public List<String> getMultiBlockNames() {
                return MultiblockHandler.getMultiblocks().stream().map(MultiblockHandler.IMultiblock::getUniqueName).collect(Collectors.toList());
            }
        });
    }

    public static void addMultiBlockGate(MultiBlockGate multiBlockGate) {
        multiBlockHandler.addMultiBlockGate(multiBlockGate);
    }
}
