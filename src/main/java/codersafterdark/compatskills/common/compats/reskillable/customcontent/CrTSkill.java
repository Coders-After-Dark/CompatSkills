package codersafterdark.compatskills.common.compats.reskillable.customcontent;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.*;

import java.util.*;
import java.util.stream.Collectors;

@ZenClass("mods.compatskills.SkillCreator")
@ZenRegister
public class CrTSkill extends Skill {
    @ZenProperty
    public IFormattedText name;

    public CrTSkill(ResourceLocation name, ResourceLocation background) {
        super(name, background);
        ReskillableRegistries.SKILLS.register(this);
    }

    private static CrTSkill createSkill(ResourceLocation name, ResourceLocation background) {
        CrTSkill customSkill = new CrTSkill(name, background);
        if (ReskillableRegistries.SKILLS.containsKey(name)) {
            Skill value = ReskillableRegistries.SKILLS.getValue(name);
            if (value instanceof CrTSkill) {
                customSkill = (CrTSkill) value;
                if (!background.equals(customSkill.getBackground())) {
                    customSkill.background = background;
                    CraftTweakerAPI.logInfo("Loaded Skill: " + name + " - Updated Background: " + background);
                } else {
                    CraftTweakerAPI.logInfo("Created or Loaded Skill: " + name + " - With Background: " + background);
                }
            }
        }
        return customSkill;
    }

    @ZenMethod
    public static CrTSkill createSkill(String name, String backGroundLocation) {
        if (CheckMethods.checkString(name) & CheckMethods.checkString(backGroundLocation)) {
            return createSkill(new ResourceLocation(CompatSkillConstants.MOD_ID, name), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @ZenMethod
    public static CrTSkill createNewSkill(String nameLocation, String backGroundLocation) {
        if (CheckMethods.checkString(nameLocation) & CheckMethods.checkString(backGroundLocation)) {
            return createSkill(new ResourceLocation(nameLocation), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @ZenGetter("levelCap")
    @ZenMethod
    public int getLevelCap() {
        return getCap();
    }

    @ZenSetter("levelCap")
    @ZenMethod
    public void setLevelCap(int cap) {
        skillConfig.setLevelCap(cap);
    }

    @ZenGetter("enabled")
    @ZenMethod
    public boolean getEnabled() {
        return isEnabled();
    }

    //TODO levelStaggering use a list of strings as input and then parse it like

    @ZenSetter("levelStaggering")
    @ZenMethod
    public void setLevelStaggering(String[] stagger) {
        //Uses code from initializing skill config
        Map<Integer, Integer> configLevelStaggering = Arrays.stream(stagger)
                .map(string -> string.split("\\|"))
                .filter(array -> array.length == 2)
                .map(array -> Pair.of(array[0], array[1]))
                .map(pair -> Pair.of(Integer.parseInt(pair.getKey()), Integer.parseInt(pair.getValue())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        Map<Integer, Integer> levelStaggering = new HashMap<>();

        int lastLevel = skillConfig.getBaseLevelCost();
        for (int i = 1; i < skillConfig.getLevelCap(); i++) {
            if (configLevelStaggering.containsKey(i)) {
                lastLevel = configLevelStaggering.get(i);
            }
            levelStaggering.put(i, lastLevel);
        }

        skillConfig.setLevelStaggering(levelStaggering);
    }

    @ZenGetter("levelStaggering")
    @ZenMethod
    public String[] getLevelStaggering() {
        List<String> stagger = new ArrayList<>();
        skillConfig.getLevelStaggering().forEach((key, value) -> stagger.add(key + "|" + value));
        return stagger.toArray(new String[0]);
    }

    @ZenSetter("enabled")
    @ZenMethod
    public void setEnabled(boolean enabled) {
        skillConfig.setEnabled(enabled);
    }

    @ZenSetter("skillPointInterval")
    @ZenMethod
    public void setSkillPointInterval(int amount) {
        skillConfig.setSkillPointInterval(amount);
    }

    @ZenGetter("baseLevelCost")
    @ZenMethod
    public int getBaseLevelCost() {
        return skillConfig.getBaseLevelCost();
    }

    @ZenSetter("baseLevelCost")
    @ZenMethod
    public void setBaseLevelCost(int cost) {
        skillConfig.setBaseLevelCost(cost);
    }

    @Override
    public String getName() {
        return name == null ? super.getName() : name.getText();
    }

    //TODO override getSpriteFromRank when we figure out a good way to say how many pictures they provide
}