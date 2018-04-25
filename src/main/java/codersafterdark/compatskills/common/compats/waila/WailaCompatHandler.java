package codersafterdark.compatskills.common.compats.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaCompatHandler {

    public static void setup() {
        FMLInterModComms.sendMessage("waila", "register", "codersafterdark.compatskills.common.compats.waila.CompatSkillsWailaRegister.callback");
    }
}
