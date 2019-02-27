package codersafterdark.compatskills.common.compats.tinkersconstruct.commands;

import codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks.MaterialLockKey;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class MaterialDumpCommand extends CraftTweakerCommand {
    public MaterialDumpCommand() {
        super("tinkermaterials");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText(TextFormatting.DARK_GREEN + "/ct tinkersmaterials", "/ct tinkersmaterials", true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Outputs a list of all materials ids/names/descriptions in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        boolean filterLocked = args.length == 1 && args[0].equalsIgnoreCase("skipLocked");
        CraftTweakerAPI.logCommand("######### Tinker's Construct Material Dump #########");
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        int size = 0;
        for (Material material : materials) {
            if (filterLocked && !LevelLockHandler.getLockByKey(new MaterialLockKey(material)).equals(LevelLockHandler.EMPTY_LOCK)) {
                continue;
            }
            CraftTweakerAPI.logCommand("##### " + material.getLocalizedName() + " #####");
            CraftTweakerAPI.logCommand("# Identifier: " + material.getIdentifier());
            CraftTweakerAPI.logCommand("# Localized: " + material.getLocalizedName());
            for (ITrait trait : material.getDefaultTraits()) {
                CraftTweakerAPI.logCommand("# Traits: ");
                CraftTweakerAPI.logCommand("## " + trait.getLocalizedName());
            }
            CraftTweakerAPI.logCommand("#####");
            size++;
        }
        CraftTweakerAPI.logCommand("#########");
        sender.sendMessage(getNormalMessage("List of Tinker's Materials Generated;"));
        sender.sendMessage(getLinkToCraftTweakerLog("List Size: " + size + " Entries;", sender));
    }

    @Override
    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.singletonList("skipLocked");
    }
}