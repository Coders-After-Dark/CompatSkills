package codersafterdark.compatskills.common.compats.reskillable;

import codersafterdark.compatskills.common.compats.reskillable.levellocking.SkillLock;
import codersafterdark.compatskills.common.compats.reskillable.levellocking.SkillLockHandler;
import net.minecraftforge.common.MinecraftForge;

public class ReskillableCompatHandler {
    private static SkillLockHandler lockHandler;

    public static void setup(){
        lockHandler = new SkillLockHandler();
        MinecraftForge.EVENT_BUS.register(lockHandler);
    }

    public static void addLevelLock(SkillLock lock){
        lockHandler.addSkillLock(lock);
    }
}
