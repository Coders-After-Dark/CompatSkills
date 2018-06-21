package codersafterdark.compatskills.common.compats.reskillable;

import codersafterdark.compatskills.common.compats.reskillable.levellocking.SkillLock;
import codersafterdark.compatskills.common.compats.reskillable.levellocking.SkillLockHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillhiding.VisibilityLock;
import codersafterdark.compatskills.common.compats.reskillable.skillhiding.VisibilityLockHandler;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.common.MinecraftForge;

public class ReskillableCompatHandler {
    private static boolean visibility, skill;

    public static void setup() {
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("!adv", input -> registry.getRequirement("not|adv|" + input));
        registry.addRequirementHandler("!trait", input -> registry.getRequirement("not|trait|" + input));
        registry.addRequirementHandler("!skill", input -> registry.getRequirement("not|" + input));
    }

    public static void addReskillableLock(LockKey key, RequirementHolder holder) {
        if (key instanceof SkillLock) {
            if (!skill) {
                MinecraftForge.EVENT_BUS.register(new SkillLockHandler());
                skill = true;
            }
        } else if (key instanceof VisibilityLock) {
            if (!visibility) {
                MinecraftForge.EVENT_BUS.register(new VisibilityLockHandler());
                visibility = true;
            }
        }
        LevelLockHandler.addLockByKey(key, holder);
    }
}