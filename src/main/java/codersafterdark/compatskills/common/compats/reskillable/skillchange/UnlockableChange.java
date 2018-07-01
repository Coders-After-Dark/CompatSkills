package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import codersafterdark.reskillable.api.unlockable.Unlockable;

import java.util.Objects;

public abstract class UnlockableChange implements SkillChange {
    protected final Unlockable unlockable;
    private final boolean unlock;

    protected UnlockableChange(Unlockable unlockable, boolean unlock) {
        this.unlockable = unlockable;
        this.unlock = unlock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unlockable, unlock);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof UnlockableChange) {
            UnlockableChange change = (UnlockableChange) o;
            return unlock == change.unlock && unlockable.equals(change.unlockable);
        }
        return false;
    }
}