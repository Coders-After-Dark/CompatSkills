package codersafterdark.compatskills.common.compats.tinkersconstruct.commands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class ModifierDumpCommand extends CraftTweakerCommand {
    public ModifierDumpCommand() {
        super("tinkersmodifiers");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\\u00A72/cttinkersmodifiers", "/ct tinkersmodifiers", true),
                getNormalMessage(" \u00A73Outputs a list of all modifier ids/names/descriptions in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        int count = 0;
        CraftTweakerAPI.logCommand("##### Tinker's Construct Modifier Dump #####");
        for (IModifier modifier : TinkerRegistry.getAllModifiers()) {
            count++;
            CraftTweakerAPI.logCommand("## " + modifier.getLocalizedName());
            CraftTweakerAPI.logCommand("#  Identifier: " + modifier.getIdentifier());
            CraftTweakerAPI.logCommand("#  Localized:  " + modifier.getLocalizedName());
            CraftTweakerAPI.logCommand("#  Description:" + modifier.getLocalizedDesc());
            CraftTweakerAPI.logCommand("##");
        }
        CraftTweakerAPI.logCommand("#########");
        sender.sendMessage(getNormalMessage("List of Tinker's Modifiers Generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + count + " Entries;", sender));
    }
}