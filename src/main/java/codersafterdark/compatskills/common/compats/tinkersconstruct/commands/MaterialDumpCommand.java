package codersafterdark.compatskills.common.compats.tinkersconstruct.commands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.Collection;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class MaterialDumpCommand extends CraftTweakerCommand {
    public MaterialDumpCommand() {
        super("tinkermaterials");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\\u00A72/cttinkersmaterials", "/ct tinkersmaterials", true),
                getNormalMessage(" \u00A73Outputs a list of all materials ids/names/descriptions in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("######### Tinker's Construct Material Dump #########");
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            CraftTweakerAPI.logCommand("##### " + material.getLocalizedName() + " #####");
            CraftTweakerAPI.logCommand("# Identifier: " + material.getIdentifier());
            CraftTweakerAPI.logCommand("# Localized: " + material.getLocalizedName());
            for (ITrait trait : material.getDefaultTraits()) {
                CraftTweakerAPI.logCommand("# Traits: ");
                CraftTweakerAPI.logCommand("## " + trait.getLocalizedName());
            }
            CraftTweakerAPI.logCommand("#####");
        }
        CraftTweakerAPI.logCommand("#########");
        sender.sendMessage(getNormalMessage("List of Tinker's Materials Generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + materials.size() + " Entries;", sender));
    }
}