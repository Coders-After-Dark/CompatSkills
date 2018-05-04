package codersafterdark.compatskills.common.compats.tinkersconstruct.modifierLocks;

import codersafterdark.reskillable.api.data.LockKey;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class ModifierLockKey implements LockKey {
    private final String id;

    public ModifierLockKey(String id) {
        this.id = id;
    }

    public ModifierLockKey(IModifier modifier) {
        this(modifier == null ? "" : modifier.getIdentifier());
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof ModifierLockKey && id.equals(((ModifierLockKey) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
