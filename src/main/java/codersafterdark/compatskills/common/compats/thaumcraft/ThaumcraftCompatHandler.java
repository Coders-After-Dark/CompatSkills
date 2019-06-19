package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftFullDump;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftResearchCategoryDump;
import codersafterdark.compatskills.common.compats.thaumcraft.commands.ThaumcraftResearchEntryDump;
import codersafterdark.compatskills.common.compats.thaumcraft.keys.KnowledgeKey;
import codersafterdark.compatskills.common.compats.thaumcraft.keys.ResearchKey;
import codersafterdark.compatskills.common.compats.thaumcraft.requirements.ResearchRequirement;
import codersafterdark.compatskills.common.compats.thaumcraft.requirements.TCInvalidateHandler;
import codersafterdark.compatskills.common.compats.thaumcraft.requirements.WarpRequirement;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.common.MinecraftForge;

public class ThaumcraftCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;
    private static boolean knowledge, research;

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

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new TCInvalidateHandler());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("warp", WarpRequirement::fromString);
        registry.addRequirementHandler("research", ResearchRequirement::fromString);
    }

    @Override
    public void loadComplete() {
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new ThaumcraftFullDump());
            CompatSkills.registerCommand(new ThaumcraftResearchCategoryDump());
            CompatSkills.registerCommand(new ThaumcraftResearchEntryDump());
        }
    }
}