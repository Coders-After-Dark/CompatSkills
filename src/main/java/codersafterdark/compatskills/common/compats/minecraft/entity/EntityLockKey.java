package codersafterdark.compatskills.common.compats.minecraft.entity;

import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.entity.Entity;

public class EntityLockKey implements LockKey {
    private final Entity entity;

    public EntityLockKey (Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof EntityLockKey && entity == ((EntityLockKey) obj).entity;
    }

    @Override
    public int hashCode() {
        return entity.hashCode();
    }
}
