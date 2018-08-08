package codersafterdark.compatskills.common.compats.dynamicswordskills;

import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.RequirementException;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import crafttweaker.mc1120.commands.CTChatCommand;
import dynamicswordskills.skills.SkillBase;
import net.minecraftforge.common.MinecraftForge;

public class DSSCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new DSSEventHandler());
        CTChatCommand.registerCommand(new DSSDumpCommand());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("dss", input -> {
            String[] parts = input.split("\\|");
            if (parts.length == 0) {
                throw new RequirementException("Invalid skill '" + input + "'.");
            }
            SkillBase skill = SkillBase.getSkillByName(parts[0]);
            if (skill == null) {
                throw new RequirementException("Invalid skill '" + parts[0] + "'.");
            }
            if (parts.length == 1) {
                throw new RequirementException("No level given for skill '" + skill.getDisplayName() + "'.");
            }
            try {
                return new DSSkillRequirement(skill, Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                throw new RequirementException("Invalid level '" + parts[1] + "' for skill: '" + skill.getDisplayName() + "'.");
            }
        });
    }
}