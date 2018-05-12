package codersafterdark.compatskills.common.compats.minecraft.tileentity;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class TileEntityCommand extends CraftTweakerCommand {
    public TileEntityCommand() {
        super("tileentityDump");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("/ct tileentityDump", "/ct tileentities", true),
                getNormalMessage("Outputs a list of all Tile Entities in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        Set<ResourceLocation> keys = TileEntity.REGISTRY.getKeys();
        CraftTweakerAPI.logCommand("Tile Entity Name Dump: ");
        keys.stream().map(ResourceLocation::toString).forEach(CraftTweakerAPI::logCommand);
        sender.sendMessage(getNormalMessage("List of Tile Entities generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + keys.size() + " Entries;", sender));
    }
}