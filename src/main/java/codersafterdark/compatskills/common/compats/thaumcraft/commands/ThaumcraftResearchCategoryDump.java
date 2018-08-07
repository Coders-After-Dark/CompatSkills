package codersafterdark.compatskills.common.compats.thaumcraft.commands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;

import java.util.LinkedHashMap;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class ThaumcraftResearchCategoryDump extends CraftTweakerCommand {
    private LinkedHashMap<String, ResearchCategory> categoryMap = ResearchCategories.researchCategories;

    public ThaumcraftResearchCategoryDump() {
        super("TCResearchDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all Research Category names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("#### ");
        CraftTweakerAPI.logCommand("### " + this.subCommandName);
        categoryMap.forEach((key, value) -> CraftTweakerAPI.logCommand("## " + key + " : " + value.researchKey));
        CraftTweakerAPI.logCommand("####");
        sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Entries Generated in Crafttweaker.log", sender));
    }
}