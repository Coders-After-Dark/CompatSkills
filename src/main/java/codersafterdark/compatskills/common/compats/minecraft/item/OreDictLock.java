package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.NBTLockKey;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class OreDictLock extends NBTLockKey {
    private String oreDict;

    public OreDictLock(String oreDict) {
        this(oreDict, null);
    }

    public OreDictLock(String oreDict, NBTTagCompound tag) {
        super(tag);
        this.oreDict = oreDict == null ? "" : oreDict;
    }

    @Override
    public LockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new OreDictLock(oreDict);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof OreDictLock && oreDict.equals(((OreDictLock) o).oreDict)) {
            return getTag() == null ? ((OreDictLock) o).getTag() == null : getTag().equals(((OreDictLock) o).getTag());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oreDict, tag);
    }
}