package codersafterdark.compatskills.common.compats.thaumcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEntry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ThaumcraftResearchDump extends CraftTweakerCommand {
    LinkedHashMap<String, ResearchCategory> categoryMap = ResearchCategories.researchCategories;


    public ThaumcraftResearchDump() {
        super("TCResearchDump");
    }

    @Override
    protected void init() {

    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("##### ");
        CraftTweakerAPI.logCommand("#### " + this.subCommandName);
        for (String key : categoryMap.keySet()) {
            Map<String, ResearchEntry> entryMap = categoryMap.get(key).research;
            CraftTweakerAPI.logCommand("### " + key + " : " + categoryMap.get(key));
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
    }
}
