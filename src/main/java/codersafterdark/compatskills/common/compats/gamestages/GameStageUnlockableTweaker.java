package codersafterdark.compatskills.common.compats.gamestages;


import codersafterdark.reskillable.api.ReskillableRegistries;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.GameStageUnlockable")
@ZenRegister
public class GameStageUnlockableTweaker {
    @ZenMethod
    public static void addGameStageUnlockable(String name, int x, int y, String skillName, int cost, String... defaultRequirements){
        ReskillableRegistries.UNLOCKABLES.register(new GameStageUnlockable(new ResourceLocation(name), x, y, new ResourceLocation(skillName), cost, defaultRequirements));
    }
}
