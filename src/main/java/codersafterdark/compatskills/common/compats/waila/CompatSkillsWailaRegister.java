package codersafterdark.compatskills.common.compats.waila;

import codersafterdark.compatskills.utils.CompatSkillConstants;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;

public class CompatSkillsWailaRegister {
    public static void callback(IWailaRegistrar registrar){
        registrar.addConfig(CompatSkillConstants.MOD_ID, "compatskills.requirements", "Requirements", true);
        registrar.registerBodyProvider(new CompatSkillsWailaDataProvider(), Block.class);
    }
}
