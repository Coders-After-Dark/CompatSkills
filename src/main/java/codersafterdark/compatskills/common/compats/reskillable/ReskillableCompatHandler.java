package codersafterdark.compatskills.common.compats.reskillable;

import codersafterdark.compatskills.common.compats.reskillable.levellocking.SkillLockHandler;
import codersafterdark.compatskills.common.invertedrequirements.InvertedAdvancement;
import codersafterdark.compatskills.common.invertedrequirements.InvertedTrait;
import codersafterdark.reskillable.api.ReskillableAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class ReskillableCompatHandler {
    private static SkillLockHandler lockHandler;

    public static void setup() {
        lockHandler = new SkillLockHandler();
        MinecraftForge.EVENT_BUS.register(lockHandler);
        ReskillableAPI.getInstance().getRequirementRegistry().addRequirementHandler("!adv", input -> new InvertedAdvancement(new ResourceLocation(input)));
        ReskillableAPI.getInstance().getRequirementRegistry().addRequirementHandler("!trait", input -> new InvertedTrait(new ResourceLocation(input)));
    }
}
