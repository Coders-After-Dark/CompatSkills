package codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks;

import codersafterdark.reskillable.api.data.LockKey;

import java.util.Objects;

public class DimensionLockKey implements LockKey {
    private final int dimension;

    public DimensionLockKey (int dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof DimensionLockKey && dimension == ((DimensionLockKey) obj).dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dimension);
    }
}
