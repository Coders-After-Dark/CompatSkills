package codersafterdark.compatskills.common.compats.minecraft.tileentity;

import codersafterdark.reskillable.api.data.LockKey;

public class TileEntityLockKey implements LockKey {
    private final String tileName;

    public TileEntityLockKey(String tileName) {
        this.tileName = tileName == null ? "" : tileName.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof TileEntityLockKey && tileName.equals(((TileEntityLockKey) o).tileName);
    }

    @Override
    public int hashCode() {
        return tileName.hashCode();
    }
}