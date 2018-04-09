package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.reskillable.api.ReskillableRegistries;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static codersafterdark.compatskills.utils.CompatSkillConstants.MOD_ID;

@ModOnly("gamestages")
@ZenClass("mods.compatskills.GameStageUnlockable")
@ZenRegister
public class GameStageUnlockableTweaker {
    @ZenMethod
    public static void addGameStageUnlockable(String gamestage, String name, int x, int y, String skillName, int cost, @Optional String... defaultRequirements) {
        ReskillableRegistries.UNLOCKABLES.register(new GameStageUnlockable(gamestage, name.replaceAll("\\s+", "").toLowerCase(), x, y, new ResourceLocation(skillName), cost, defaultRequirements));
        CraftTweakerAPI.logInfo("Added Unlockable Trait: " + name + " With GameStage Link: " + gamestage + " under Skill: " + skillName);
        CraftTweakerAPI.logInfo("Unlockable Trait: " + name + " has requirements: " + Arrays.toString(defaultRequirements));
    }
}
