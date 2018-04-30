package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.LockKey;

import java.util.Objects;

public class RitualCrystalLockKey implements LockKey {
    private final int crystalLevel;

    public  RitualCrystalLockKey(int crystalLevel) {
        this.crystalLevel = crystalLevel;
    }

    public RitualCrystalLockKey(Ritual ritual) {
        this(ritual == null ? -1 : ritual.getCrystalLevel());
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof RitualCrystalLockKey && crystalLevel == ((RitualCrystalLockKey) o).crystalLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crystalLevel);
    }
}