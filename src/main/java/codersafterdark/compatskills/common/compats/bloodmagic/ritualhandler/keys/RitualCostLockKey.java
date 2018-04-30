package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.LockKey;

import java.util.Objects;

public class RitualCostLockKey implements LockKey {
    //TODO once eventually there is a way to compare lock values make it so that if the activation cost is greater than the stored lock one, then the lock should count
    private final int activationCost;

    public  RitualCostLockKey(int activationCost) {
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
        return Objects.hashCode(activationCost);
    }
}