package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import codersafterdark.compatskills.utils.CompatModuleBase;
import net.minecraftforge.common.MinecraftForge;

public class BetterQuestingHandler extends CompatModuleBase {
    public static boolean ENABLED;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new BQTaskHandler());
    }

    @Override
    public void postInit() {
        QuestingAPI.getAPI(ApiReference.TASK_REG).register(FactoryTaskRequirement.INSTANCE);
    }
}