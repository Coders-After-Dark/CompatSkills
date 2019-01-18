package codersafterdark.compatskills.common.compats.oreexcavator;

import codersafterdark.reskillable.api.data.LockKey;

public class ExcavationShapeKey implements LockKey {
    private final String name;

    public ExcavationShapeKey(String name) {
        this.name = name == null ? "" : name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ExcavationShapeKey && name.equals(((ExcavationShapeKey) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}