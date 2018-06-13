package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;

import java.util.Objects;

public class EMCLockKey implements FuzzyLockKey {
    private final int emc;

    public EMCLockKey(int emc) {
        this.emc = emc;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof EMCLockKey && emc == ((EMCLockKey) o).emc;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emc);
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof EMCLockKey && emc >= ((EMCLockKey) o).emc;
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