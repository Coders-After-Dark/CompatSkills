package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.annotations.ZenRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.compatskills.Skill")
@ZenRegister
public class CTSkill {
    final Skill skill;

    public CTSkill(Skill skill) {
        this.skill = skill;
    }

    @ZenGetter("key")
    @ZenMethod
    public String getKey() {
        return skill.getKey();
    }

    @ZenGetter("name")
    @ZenMethod
    public String getName() {
        return skill.getName();
    }

    @ZenGetter("backgroundLocation")
    @ZenMethod
    public String getBackgroundLocation() {
        return skill.getBackground().toString();
    }

    @ZenSetter("backgroundLocation")
    @ZenMethod
    public void setBackgroundLocation(String resourceLocation) {
        if (CheckMethods.checkResourceLocation(resourceLocation)) {
            skill.setBackground(new ResourceLocation(resourceLocation));
        }
    }

    @ZenGetter("cap")
    @ZenMethod
    public int getCap() {
        return skill.getCap();
    }

    @ZenSetter("cap")
    @ZenMethod
    public void setCap(int cap) {
        skill.getSkillConfig().setLevelCap(cap);
    }

    @ZenGetter("enabled")
    @ZenMethod
    public boolean getEnabled() {
        return skill.isEnabled();
    }

    @ZenSetter("enabled")
    @ZenMethod
    public void setEnabled(boolean enabled) {
        skill.getSkillConfig().setEnabled(enabled);
    }

    @ZenGetter("hidden")
    @ZenMethod
    public boolean isHidden() {
        return skill.isHidden();
    }

    @ZenSetter("hidden")
    @ZenMethod
    public void setHidden(boolean hidden) {
        skill.setHidden(hidden);
    }

    @ZenGetter("levelButton")
    @ZenMethod
    public boolean hasLevelButton() {
        return skill.hasLevelButton();
    }

    @ZenSetter("levelButton")
    @ZenMethod
    public void setLevelButton(boolean button) {
        skill.getSkillConfig().setLevelButton(button);
    }

    @ZenOperator(OperatorType.COMPARE)
    public int compareTo(CTSkill other) {
        return skill.compareTo(other.skill);
    }

    @ZenGetter("skillPointInterval")
    @ZenMethod
    public int getSkillPointInterval() {
        return skill.getSkillPointInterval();
    }

    @ZenSetter("skillPointInterval")
    @ZenMethod
    public void setSkillPointInterval(int amount) {
        skill.getSkillConfig().setSkillPointInterval(amount);
    }

    @ZenMethod
    public int getLevelUpCost(int level) {
        return skill.getLevelUpCost(level);
    }

    public Skill getSkill() {
        return skill;
    }

    @ZenGetter("baseLevelCost")
    @ZenMethod
    public int getBaseLevelCost() {
        return skill.getSkillConfig().getBaseLevelCost();
    }

    @ZenSetter("baseLevelCost")
    @ZenMethod
    public void setBaseLevelCost(int cost) {
        skill.getSkillConfig().setBaseLevelCost(cost);
    }

    @ZenGetter("levelStaggering")
    @ZenMethod
    public String[] getLevelStaggering() {
        List<String> stagger = new ArrayList<>();
        skill.getSkillConfig().getLevelStaggering().forEach((key, value) -> stagger.add(key + "|" + value));
        return stagger.toArray(new String[0]);
    }

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

        int lastLevel = skill.getSkillConfig().getBaseLevelCost();
        for (int i = 1; i < skill.getSkillConfig().getLevelCap(); i++) {
            if (configLevelStaggering.containsKey(i)) {
                lastLevel = configLevelStaggering.get(i);
            }
            levelStaggering.put(i, lastLevel);
        }

        skill.getSkillConfig().setLevelStaggering(levelStaggering);
    }
}