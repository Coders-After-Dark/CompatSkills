package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.base.ToolTipHandler;
import moze_intel.projecte.gameObjs.gui.GUICondenser;
import moze_intel.projecte.gameObjs.gui.GUICondenserMK2;
import moze_intel.projecte.gameObjs.gui.GUITransmutation;
import net.minecraftforge.common.MinecraftForge;

public class ProjectECompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    private TransmutationLockHandler handler = new TransmutationLockHandler();

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(handler);
    }

    @Override
    public void clientPreInit() {
        ToolTipHandler.addTooltipInjector(GUITransmutation.class, handler::transmutationTooltip);
        ToolTipHandler.addTooltipInjector(GUICondenser.class, handler::condenserTooltip);
        //TODO: Remove if statement in 1.13 as all versions of ProjectE for 1.13 will not need it
        if (!GUICondenser.class.isAssignableFrom(GUICondenserMK2.class)) {
            //Versions > PE 1.4.0 have GUICondenserMK2 extend GUICondenser.
            //This prevents the double tooltip
            ToolTipHandler.addTooltipInjector(GUICondenserMK2.class, handler::condenserTooltip);
        }
    }
}