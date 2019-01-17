package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.compatskills.common.compats.minecraft.drops.ItemStackDropKey;
import codersafterdark.reskillable.api.data.LockKey;

public class ExcavationShapeKey implements LockKey {
    private final String name;

    public ExcavationShapeKey(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ItemStackDropKey && name.equals(((ExcavationShapeKey) obj).name);
    }
}
