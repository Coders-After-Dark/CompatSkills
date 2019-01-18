package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.common.MinecraftForge;

public class OreExcavationCompatHandler extends CompatModuleBase {
    private static RequirementHolder holder = LevelLockHandler.EMPTY_LOCK;
    public static boolean ENABLED;
    private static boolean hasLocks;

    @Override
    public void preInit() {
        ENABLED = true;
    }

    @Override
    public void loadComplete() {
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new ShapeDumpCommand());
        }
    }

    public static void addOERequirements(RequirementHolder requirementHolder) {
        if (holder.equals(LevelLockHandler.EMPTY_LOCK)) {
            holder = requirementHolder;
            registerListener();
        } else {
            holder = new RequirementHolder(holder, requirementHolder);
        }
    }

    public static void registerListener() {
        if (!hasLocks) {
            MinecraftForge.EVENT_BUS.register(new ExcavationLockHandler());
            hasLocks = true;
        }
    }

    public static RequirementHolder getHolder() {
        return holder;
    }
}