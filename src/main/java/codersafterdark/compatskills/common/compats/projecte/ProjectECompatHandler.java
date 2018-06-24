package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.base.ToolTipHandler;
import moze_intel.projecte.gameObjs.gui.GUICondenser;
import moze_intel.projecte.gameObjs.gui.GUICondenserMK2;
import moze_intel.projecte.gameObjs.gui.GUITransmutation;
import net.minecraftforge.common.MinecraftForge;

public class ProjectECompatHandler extends CompatModuleBase {
    private TransmutationLockHandler handler = new TransmutationLockHandler();

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(handler);
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void clientPreInit() {
        ToolTipHandler.addTooltipInjector(GUITransmutation.class, handler::transmutationTooltip);
        ToolTipHandler.addTooltipInjector(GUICondenser.class, handler::condenserTooltip);
        ToolTipHandler.addTooltipInjector(GUICondenserMK2.class, handler::condenserTooltip); //The class does not extend the other one so it is also needed
    }
}