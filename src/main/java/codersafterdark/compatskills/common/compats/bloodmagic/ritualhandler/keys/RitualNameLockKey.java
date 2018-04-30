package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.reskillable.api.data.LockKey;

public class RitualNameLockKey implements LockKey {
    private final String name;

    public  RitualNameLockKey(String name) {
        this.name = name;
    }

    public RitualNameLockKey(Ritual ritual) {
        this(ritual == null ? "" : ritual.getName());
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof RitualNameLockKey && name.equals(((RitualNameLockKey) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}