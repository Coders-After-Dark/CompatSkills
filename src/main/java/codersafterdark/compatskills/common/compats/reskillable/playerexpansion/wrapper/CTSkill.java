package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.annotations.ZenRegister;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @ZenGetter("levelStaggering")
    @ZenMethod
    public String[] getLevelStaggering() {
        List<String> stagger = new ArrayList<>();
        skill.getSkillConfig().getLevelStaggering().forEach((key, value) -> stagger.add(key + "|" + value));
        return stagger.toArray(new String[0]);
    }
}