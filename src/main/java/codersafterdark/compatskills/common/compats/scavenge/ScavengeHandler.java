package codersafterdark.compatskills.common.compats.scavenge;

import codersafterdark.compatskills.utils.CompatModuleBase;

public class ScavengeHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
    }
}