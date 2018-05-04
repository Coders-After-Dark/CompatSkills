package codersafterdark.compatskills.utils.multiblock;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.List;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public abstract class MultiBlockCommand extends CraftTweakerCommand {
    public MultiBlockCommand(String setName) {
        super(setName + "MultiBlocks");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(" \u00A73Outputs a list of all " + subCommandName + "names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("#### ");
        CraftTweakerAPI.logCommand("### " + subCommandName + ":");
        List<String> multiBlockList = getMultiBlockNames();
        CraftTweakerAPI.logCommand("## Multiblocks");
        for (String name : multiBlockList) {
            CraftTweakerAPI.logCommand("# " + name);
        }
        CraftTweakerAPI.logCommand("####");
        sender.sendMessage(getNormalMessage("List of " + subCommandName + " generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + multiBlockList.size() + " Entries;", sender));
    }

    public abstract List<String> getMultiBlockNames();
}