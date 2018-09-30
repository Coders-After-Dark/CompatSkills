package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.RitualHandler;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCostLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCrystalLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualNameLockKey;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashSet;
import java.util.Set;

public class BMCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    private static Set<Class<? extends LockKey>> lockTypes = new HashSet<>();
    private static boolean ritual;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new BindHandler());
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new RitualCommand());
        }
    }

    public static void addBMLock(LockKey key, RequirementHolder holder) {
        if (key instanceof RitualNameLockKey) {
            registerRitualLock(RitualNameLockKey.class);
            registerRitual();
        } else if (key instanceof RitualCrystalLockKey) {
            registerRitualLock(RitualCrystalLockKey.class);
            registerRitual();
        } else if (key instanceof RitualCostLockKey) {
            registerRitualLock(RitualCostLockKey.class);
            registerRitual();
        }
        LevelLockHandler.addLockByKey(key, holder);
    }

    private static void registerRitual() {
        if (!ritual) {
            MinecraftForge.EVENT_BUS.register(new RitualHandler());
            ritual = true;
        }
    }

    private static void registerRitualLock(Class<? extends LockKey> ritualLockType) {
        if (!lockTypes.contains(ritualLockType)) {
            LevelLockHandler.registerLockKey(Ritual.class, ritualLockType);
            lockTypes.add(ritualLockType);
        }
    }
}