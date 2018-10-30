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

    public static void registerExpansion() {
        if (ENABLED) {
            QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskRequirement.INSTANCE);
        }
    }
}