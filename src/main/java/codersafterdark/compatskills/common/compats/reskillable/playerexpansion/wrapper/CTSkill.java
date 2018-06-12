package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

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

    @ZenGetter("enabled")
    @ZenMethod
    public boolean getEnabled() {
        return skill.isEnabled();
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

    @ZenMethod
    public int getLevelUpCost(int level) {
        return skill.getLevelUpCost(level);
    }

    public Skill getSkill() {
        return skill;
    }
}