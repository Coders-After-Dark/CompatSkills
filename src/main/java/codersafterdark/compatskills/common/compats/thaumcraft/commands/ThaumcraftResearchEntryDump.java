package codersafterdark.compatskills.common.compats.thaumcraft.commands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEntry;

import java.util.LinkedHashMap;
import java.util.Map;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class ThaumcraftResearchEntryDump extends CraftTweakerCommand {
    LinkedHashMap<String, ResearchCategory> categoryMap = ResearchCategories.researchCategories;

    public ThaumcraftResearchEntryDump() {
        super("TCResearchEntryDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all Research Entries keys in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("####");
        CraftTweakerAPI.logCommand("### "+ subCommandName);
        for (String key : categoryMap.keySet()) {
            Map<String, ResearchEntry> entryMap = categoryMap.get(key).research;
            for (String entryKey : entryMap.keySet()) {
                CraftTweakerAPI.logCommand("## Name: " + entryMap.get(entryKey).getLocalizedName());
                CraftTweakerAPI.logCommand("## Key:  " + entryMap.get(entryKey).getKey());
                CraftTweakerAPI.logCommand("####");
            }
        }
        CraftTweakerAPI.logCommand("####");
        sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Entries Generated in Crafttweaker.log", sender));
    }
}
