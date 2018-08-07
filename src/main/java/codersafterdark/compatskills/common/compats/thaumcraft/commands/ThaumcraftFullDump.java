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

public class ThaumcraftFullDump extends CraftTweakerCommand {
    private LinkedHashMap<String, ResearchCategory> categoryMap = ResearchCategories.researchCategories;

    public ThaumcraftFullDump() {
        super("TCFullDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all Research Categories, Entries, etc in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("##### ");
        CraftTweakerAPI.logCommand("#### " + this.subCommandName);
        for (Map.Entry<String, ResearchCategory> entries : categoryMap.entrySet()) {
            Map<String, ResearchEntry> entryMap = entries.getValue().research;
            CraftTweakerAPI.logCommand("### " + entries.getKey());
            CraftTweakerAPI.logCommand("## With Entries: ");
            for (String subKey : entryMap.keySet()) {
                ResearchEntry entry = entryMap.get(subKey);
                CraftTweakerAPI.logCommand("#####");
                CraftTweakerAPI.logCommand("#### Key:              " + entry.getKey());
                CraftTweakerAPI.logCommand("###  Category:         " + entry.getCategory());
                CraftTweakerAPI.logCommand("##   Localized Name:   " + entry.getLocalizedName());
                CraftTweakerAPI.logCommand("##   Unlocalized Name: " + entry.getName());
            }
            CraftTweakerAPI.logCommand("");
            CraftTweakerAPI.logCommand("");
        }
        sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Entries Generated in Crafttweaker.log", sender));
    }
}