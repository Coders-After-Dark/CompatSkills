package codersafterdark.compatskills.common.compats.theoneprobe;

import codersafterdark.compatskills.utils.CompatModuleBase;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPCompatHandler extends CompatModuleBase {
    @Override
    public void preInit() {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "codersafterdark.compatskills.common.compats.theoneprobe.CompatSkillsTOPSupport");
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }
}