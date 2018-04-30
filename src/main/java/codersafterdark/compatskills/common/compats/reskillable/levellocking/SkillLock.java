package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.skill.Skill;

import java.util.Objects;

public class SkillLock implements LockKey {
    private final Skill skill;
    private final int level;

    public SkillLock(Skill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SkillLock) {
            SkillLock other = (SkillLock) o;
            return skill == null ? other.skill == null && level == other.level : skill.equals(other.skill) && level == other.level;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, level);
    }
}
