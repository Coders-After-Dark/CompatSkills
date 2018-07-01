package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import codersafterdark.reskillable.api.skill.Skill;

import java.util.Objects;

public class SkillLevel implements SkillChange {
    private final Skill skill;
    private final int level;

    public SkillLevel(Skill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, level);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SkillLevel) {
            SkillLevel change = (SkillLevel) o;
            return level == change.level && skill.equals(change.skill);
        }
        return false;
    }
}