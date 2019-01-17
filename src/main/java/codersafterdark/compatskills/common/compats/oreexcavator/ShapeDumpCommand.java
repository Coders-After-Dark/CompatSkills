package codersafterdark.compatskills.common.compats.oreexcavator;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import oreexcavation.shapes.ExcavateShape;
import oreexcavation.shapes.ShapeRegistry;

import java.util.List;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class ShapeDumpCommand extends CraftTweakerCommand {
    public ShapeDumpCommand() {
        super("OEShapeDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all Ore Excavation Shape names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        List<ExcavateShape> shapes = ShapeRegistry.INSTANCE.getShapeList();
        CraftTweakerAPI.logCommand("#### ");
        CraftTweakerAPI.logCommand("### " + this.subCommandName);
        for (ExcavateShape shape : shapes) {
            CraftTweakerAPI.logCommand("## " + shape.getName());
        }
        CraftTweakerAPI.logCommand("####");
        sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog(shapes.size() + " Entries Generated in Crafttweaker.log", sender));
    }
}