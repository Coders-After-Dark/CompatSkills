package codersafterdark.compatskills.common.compats.gamestages.gamestageunlockable;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.ReskillableRegistries;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ModOnly("gamestages")
@ZenClass("mods.compatskills.GameStageUnlockable")
@ZenRegister
public class GameStageUnlockableTweaker {
    @ZenMethod
    public static void addGameStageUnlockable(String gamestage, String name, int x, int y, String skillName, int cost, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddGameStageUnlockable(gamestage, name, x, y, skillName, cost, requirements));
    }

    private static class AddGameStageUnlockable implements IAction {
        String gameStage;
        String name;
        int x;
        int y;
        String skillName;
        int cost;
        String[] requirements;

        AddGameStageUnlockable(String gamestage, String name, int x, int y, String skillName, int cost, String... requirements) {
            if (CheckMethods.checkString(gamestage) && CheckMethods.checkString(name) && CheckMethods.checkIntX(x) && CheckMethods.checkIntY(y) && CheckMethods.checkParentSkillsString(skillName) && CheckMethods.checkInt(cost) && CheckMethods.checkStringArray(requirements)) {
                this.gameStage = gamestage;
                this.name = name;
                this.x = x;
                this.y = y;
                this.skillName = skillName;
                this.cost = cost;
                this.requirements = requirements;
            }
        }

        @Override
        public void apply() {
            ReskillableRegistries.UNLOCKABLES.register(new GameStageUnlockable(gameStage, name.replaceAll("\\s+", "").toLowerCase(), x, y, new ResourceLocation(skillName), cost, requirements));
        }

        @Override
        public String describe() {
            CraftTweakerAPI.logInfo("Added Unlockable Trait: " + name + " With GameStage Link: " + gameStage + " under Skill: " + skillName);
            CraftTweakerAPI.logInfo("Unlockable Trait: " + name + " has requirements: " + Arrays.toString(requirements));
            return null;
        }
    }
}