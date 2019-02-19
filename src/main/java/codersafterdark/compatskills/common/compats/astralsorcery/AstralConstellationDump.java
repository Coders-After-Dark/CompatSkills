package codersafterdark.compatskills.common.compats.astralsorcery;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import hellfirepvp.astralsorcery.common.constellation.ConstellationRegistry;
import hellfirepvp.astralsorcery.common.constellation.IMajorConstellation;
import hellfirepvp.astralsorcery.common.constellation.IMinorConstellation;
import hellfirepvp.astralsorcery.common.constellation.IWeakConstellation;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class AstralConstellationDump extends CraftTweakerCommand {
    public AstralConstellationDump() {
        super("astralConstellations");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all Astral Sorcery Constellation names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("#### ");
        CraftTweakerAPI.logCommand("### " + this.subCommandName);
        ConstellationRegistry.getAllConstellations().forEach(constellation -> {
            CraftTweakerAPI.logCommand("## Name: " + constellation.getSimpleName());
            CraftTweakerAPI.logCommand("## Unlocalized Name: " + constellation.getUnlocalizedName());
            if (constellation instanceof IMajorConstellation) {
                CraftTweakerAPI.logCommand("## Constellation type: Major");
            } else if (constellation instanceof IMinorConstellation) {
                CraftTweakerAPI.logCommand("## Constellation type: Minor");
            } else if (constellation instanceof IWeakConstellation) {
                CraftTweakerAPI.logCommand("## Constellation type: Weak");
            }
            CraftTweakerAPI.logCommand("####");
        });
        CraftTweakerAPI.logCommand("####");
        sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Entries Generated in Crafttweaker.log", sender));
    }
}