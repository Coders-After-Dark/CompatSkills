package codersafterdark.compatskills.common.compats.waila;

import codersafterdark.compatskills.utils.CompatSkillConstants;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.block.Block;

@WailaPlugin
public class CompatSkillsWailaRegister implements IWailaPlugin {
    @Override
    public void register(IWailaRegistrar registrar) {
        registrar.addConfig(CompatSkillConstants.MOD_ID, "compatskills.requirements", "Requirements", true);
        registrar.registerBodyProvider(new CompatSkillsWailaDataProvider(), Block.class);
    }
}