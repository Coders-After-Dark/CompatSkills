package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftFullDump;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftResearchCategoryDump;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftResearchEntryDump;
import codersafterdark.compatskills.common.compats.thaumcraft.keys.KnowledgeKey;
import codersafterdark.compatskills.common.compats.thaumcraft.keys.ResearchKey;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;

public class ThaumcraftCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;
    private static boolean knowledge, research;

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

    public static void addThaumcraftLock(LockKey key, RequirementHolder holder) {
        if (key instanceof KnowledgeKey) {
            if (!knowledge) {
                MinecraftForge.EVENT_BUS.register(new KnowledgeHandler());
                knowledge = true;
            }
        } else if (key instanceof ResearchKey) {
            if (!research) {
                MinecraftForge.EVENT_BUS.register(new ResearchHandler());
                research = true;
            }
        }
        LevelLockHandler.addLockByKey(key, holder);
    }
}