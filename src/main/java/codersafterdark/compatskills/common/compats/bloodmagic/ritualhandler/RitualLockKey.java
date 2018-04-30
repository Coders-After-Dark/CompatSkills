package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.LockKey;

public class RitualLockKey implements LockKey {
    private final Ritual ritual;

    public RitualLockKey(Ritual ritual) {
        this.ritual = ritual;
    }

    public Ritual getRitual() {
        return ritual;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof RitualLockKey && ritual.equals(((RitualLockKey) o).ritual);
    }

    @Override
    public int hashCode() {
        return ritual.hashCode();
    }
}