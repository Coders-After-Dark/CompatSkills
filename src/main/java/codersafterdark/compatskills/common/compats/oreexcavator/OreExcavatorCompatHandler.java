package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CompatModuleBase;
import net.minecraftforge.common.MinecraftForge;

public class OreExcavatorCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new ExcavationLockHandler());
    }

    @Override
    public void loadComplete() {
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new ShapeDumpCommand());
        }
    }
}