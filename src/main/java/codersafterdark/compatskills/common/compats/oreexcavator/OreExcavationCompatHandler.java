package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.common.MinecraftForge;

public class OreExcavationCompatHandler extends CompatModuleBase {
    private static RequirementHolder holder = LevelLockHandler.EMPTY_LOCK;
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
    }

    public static void addOERequirements(RequirementHolder requirementHolder) {
        if (holder.equals(LevelLockHandler.EMPTY_LOCK)) {
            holder = requirementHolder;
            MinecraftForge.EVENT_BUS.register(new ExcavationLockHandler());
        } else {
            holder = new RequirementHolder(holder, requirementHolder);
        }
    }

    public static RequirementHolder getHolder() {
        return holder;
    }
}