package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;

import java.util.Objects;

public class RitualCrystalLockKey implements FuzzyLockKey {
    private final int crystalLevel;

    public RitualCrystalLockKey(int crystalLevel) {
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

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof RitualCrystalLockKey && crystalLevel >= ((RitualCrystalLockKey) o).crystalLevel;
    }

    @Override
    public boolean isNotFuzzy() {
        return false;
    }

    @Override
    public LockKey getNotFuzzy() {
        return null;
    }
}