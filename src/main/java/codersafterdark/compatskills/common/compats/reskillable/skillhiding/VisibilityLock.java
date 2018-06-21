package codersafterdark.compatskills.common.compats.reskillable.skillhiding;

import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.skill.Skill;

public class VisibilityLock implements LockKey {
    private final Skill skill;

    public VisibilityLock(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof VisibilityLock) {
            VisibilityLock other = (VisibilityLock) obj;
            return skill == null ? other.skill == null : skill.equals(other.skill);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return skill.hashCode();
    }
}