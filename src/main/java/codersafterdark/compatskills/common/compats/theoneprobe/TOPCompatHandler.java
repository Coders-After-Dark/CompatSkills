package codersafterdark.compatskills.common.compats.theoneprobe;

import codersafterdark.compatskills.utils.CompatModuleBase;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "codersafterdark.compatskills.common.compats.theoneprobe.CompatSkillsTOPSupport");
    }
}