package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftFullDump;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftResearchCategoryDump;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftResearchEntryDump;
import codersafterdark.compatskills.utils.CompatModuleBase;
import crafttweaker.mc1120.commands.CTChatCommand;

public class ThaumcraftCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
    }

    @Override
    public void loadComplete() {
        CTChatCommand.registerCommand(new ThaumcraftFullDump());
        CTChatCommand.registerCommand(new ThaumcraftResearchCategoryDump());
        CTChatCommand.registerCommand(new ThaumcraftResearchEntryDump());
    }
}
