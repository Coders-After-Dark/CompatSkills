package codersafterdark.compatskills.common.compats.dynamicswordskills;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import net.minecraftforge.common.MinecraftForge;

public class DSSCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new DSSEventHandler());
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new DSSDumpCommand());
        }
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("dss", DSSkillRequirement::fromString);
    }
}