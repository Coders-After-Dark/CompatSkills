package codersafterdark.compatskills.common.compats.tinkersconstruct.commands;

import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockKey;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.Collection;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class ModifierDumpCommand extends CraftTweakerCommand {
    public ModifierDumpCommand() {
        super("tinkersmodifiers");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct tinkersmodifiers", "/ct tinkersmodifiers", true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all modifier ids/names/descriptions in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        boolean filterLocked = args.length == 1 && args[0].equalsIgnoreCase("skipLocked");
        CraftTweakerAPI.logCommand("##### Tinker's Construct Modifier Dump #####");
        Collection<IModifier> modifiers = TinkerRegistry.getAllModifiers();
        int size = 0;
        for (IModifier modifier : modifiers) {
            if (!modifier.isHidden()) {
                if (filterLocked && !LevelLockHandler.getLockByKey(new ModifierLockKey(modifier)).equals(LevelLockHandler.EMPTY_LOCK)) {
                    continue;
                }
                CraftTweakerAPI.logCommand("## " + modifier.getLocalizedName());
                CraftTweakerAPI.logCommand("#  Identifier: " + modifier.getIdentifier());
                CraftTweakerAPI.logCommand("#  Localized:  " + modifier.getLocalizedName());
                CraftTweakerAPI.logCommand("#  Description:" + modifier.getLocalizedDesc());
                CraftTweakerAPI.logCommand("##");
                size++;
            }
        }
        CraftTweakerAPI.logCommand("#########");
        sender.sendMessage(getNormalMessage("List of Tinker's Modifiers Generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + size + " Entries;", sender));
    }
}