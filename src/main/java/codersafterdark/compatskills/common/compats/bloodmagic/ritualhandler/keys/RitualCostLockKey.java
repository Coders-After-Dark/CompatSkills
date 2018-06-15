package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;

import java.util.Objects;

public class RitualCostLockKey implements FuzzyLockKey {
    private final int activationCost;

    public RitualCostLockKey(int activationCost) {
        this.activationCost = activationCost;
    }

    public RitualCostLockKey(Ritual ritual) {
        this(ritual == null ? -1 : ritual.getActivationCost());
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof RitualCostLockKey && activationCost == ((RitualCostLockKey) o).activationCost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(activationCost);
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof RitualCostLockKey && activationCost >= ((RitualCostLockKey) o).activationCost;
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