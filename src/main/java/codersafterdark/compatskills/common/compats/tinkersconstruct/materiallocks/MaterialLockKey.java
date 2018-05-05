package codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks;

import codersafterdark.reskillable.api.data.LockKey;
import slimeknights.tconstruct.library.materials.Material;

public class MaterialLockKey implements LockKey {
    private final Material material;

    public MaterialLockKey(Material material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MaterialLockKey) {
            MaterialLockKey other = (MaterialLockKey) o;
            return material == null ? other.material == null : material.equals(other.material);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return material == null ? super.hashCode() : material.hashCode();
    }
}
