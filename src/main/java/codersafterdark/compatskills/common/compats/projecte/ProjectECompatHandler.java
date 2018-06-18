package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.reskillable.base.ToolTipHandler;
import moze_intel.projecte.gameObjs.gui.GUICondenser;
import moze_intel.projecte.gameObjs.gui.GUICondenserMK2;
import moze_intel.projecte.gameObjs.gui.GUITransmutation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

public class ProjectECompatHandler {
    public static void setup(Side side) {
        TransmutationLockHandler handler = new TransmutationLockHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        if (side.isClient()) {
            ToolTipHandler.addTooltipInjector(GUITransmutation.class, handler::transmutationTooltip);
            ToolTipHandler.addTooltipInjector(GUICondenser.class, handler::condenserTooltip);
            ToolTipHandler.addTooltipInjector(GUICondenserMK2.class, handler::condenserTooltip); //The class does not extend the other one so it is also needed
        }
    }
}