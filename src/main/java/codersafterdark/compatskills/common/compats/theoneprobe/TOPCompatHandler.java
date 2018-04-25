package codersafterdark.compatskills.common.compats.theoneprobe;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPCompatHandler {
    public static void setup() {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "codersafterdark.compatskills.common.compats.theoneprobe.CompatSkillsTOPSupport");
    }
}
