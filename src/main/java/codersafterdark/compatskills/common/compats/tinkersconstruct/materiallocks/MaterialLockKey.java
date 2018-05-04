package codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks;

import codersafterdark.reskillable.api.data.LockKey;
import slimeknights.tconstruct.library.materials.Material;

public class MaterialLockKey implements LockKey {
    final String id;

    public MaterialLockKey(String id) {
        this.id = id;
    }

    public MaterialLockKey(Material material) {
        this(material == null ? "" : material.getIdentifier());
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof MaterialLockKey && id.equals(((MaterialLockKey) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
