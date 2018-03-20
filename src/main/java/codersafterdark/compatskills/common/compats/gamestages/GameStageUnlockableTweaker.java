package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.ReskillableRegistries;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static codersafterdark.compatskills.lib.LibMisc.MOD_ID;

@ModOnly("gamestages")
@ZenClass("mods.compatskills.GameStageUnlockable")
@ZenRegister
public class GameStageUnlockableTweaker {
    @ZenMethod
    public static void addGameStageUnlockable(String gamestage, String name, int x, int y, String skillName, int cost, @Optional String... defaultRequirements){
        ReskillableRegistries.UNLOCKABLES.register(new GameStageUnlockable(gamestage, new ResourceLocation(MOD_ID, name), x, y, new ResourceLocation(skillName), cost, defaultRequirements));
    }
}
