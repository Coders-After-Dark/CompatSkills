package codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks;

import codersafterdark.reskillable.api.data.LockKey;
import slimeknights.tconstruct.library.modifiers.IToolMod;

public class ModifierLockKey implements LockKey {
    private final IToolMod modifier;

    public ModifierLockKey(IToolMod modifier) {
        this.modifier = modifier;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ModifierLockKey) {
            ModifierLockKey other = (ModifierLockKey) o;
            return modifier == null ? other.modifier == null : modifier.getIdentifier().equals(other.modifier.getIdentifier());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return modifier == null ? super.hashCode() : modifier.getIdentifier().hashCode();
    }
}
