package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.base.ToolTipHandler;
import com.latmod.mods.projectex.gui.GuiLink;
import moze_intel.projecte.gameObjs.gui.GUICondenser;
import moze_intel.projecte.gameObjs.gui.GUICondenserMK2;
import moze_intel.projecte.gameObjs.gui.GUITransmutation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

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
        //TODO: This probably will eventually extend GUICondenser and when that is the case remove this extra line
        ToolTipHandler.addTooltipInjector(GUICondenserMK2.class, handler::condenserTooltip);

        if (Loader.isModLoaded("projectex")) {
            ToolTipHandler.addTooltipInjector(GuiLink.class, handler::condenserTooltip);
        }
    }
}