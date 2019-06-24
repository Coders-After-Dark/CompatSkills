package codersafterdark.compatskills.common.compats.reskillable.skillhiding;

import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.skill.Skill;
import java.util.Objects;

public class VisibilityLock implements LockKey {
    private final Skill skill;

    public VisibilityLock(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof VisibilityLock && Objects.equals(skill, ((VisibilityLock) o).skill);
    }

    @Override
    public int hashCode() {
        return skill.hashCode();
    }
}