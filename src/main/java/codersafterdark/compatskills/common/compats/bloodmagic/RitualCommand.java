package codersafterdark.compatskills.common.compats.bloodmagic;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.List;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public abstract class RitualCommand extends CraftTweakerCommand {
    public RitualCommand() {
        super("ritualDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("/ct ritualDump", "/ct rituals", true),
                getNormalMessage("Outputs a list of all " + "Rituals " + "names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("Ritual Dump: ");
        List<String> ritualList = getRitualNames();
        for (String ritual : ritualList) {
            CraftTweakerAPI.logCommand(ritual);
        }
        sender.sendMessage(getNormalMessage("List of Rituals generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + ritualList.size() + " Entries;", sender));
    }

    public abstract List<String> getRitualNames();
}
