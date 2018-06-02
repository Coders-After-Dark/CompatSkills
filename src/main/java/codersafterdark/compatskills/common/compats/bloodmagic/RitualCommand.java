package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.ritual.RitualRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class RitualCommand extends CraftTweakerCommand {
    public RitualCommand() {
        super("ritualDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct ritualDump", "/ct rituals", true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all Rituals names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("Ritual Dump: ");
        List<String> ritualList = RitualRegistry.getIds();
        ritualList.forEach(CraftTweakerAPI::logCommand);
        sender.sendMessage(getNormalMessage("List of Rituals generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + ritualList.size() + " Entries;", sender));
    }
}