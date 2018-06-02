package codersafterdark.compatskills.common.compats.reskillable;

import codersafterdark.compatskills.common.compats.reskillable.levellocking.SkillLockHandler;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import net.minecraftforge.common.MinecraftForge;

public class ReskillableCompatHandler {
    public static void setup() {
        MinecraftForge.EVENT_BUS.register(new SkillLockHandler());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("!adv", input -> registry.getRequirement("not|adv|" + input));
        registry.addRequirementHandler("!trait", input -> registry.getRequirement("not|trait|" + input));
        registry.addRequirementHandler("!skill", input -> registry.getRequirement("not|" + input));
    }
}