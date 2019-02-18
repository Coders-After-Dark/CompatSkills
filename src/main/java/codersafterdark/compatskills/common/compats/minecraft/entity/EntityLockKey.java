package codersafterdark.compatskills.common.compats.minecraft.entity;

import codersafterdark.reskillable.api.data.LockKey;

public abstract class EntityLockKey implements LockKey {
    private final String entityID;

    protected EntityLockKey(String entityID) {
        this.entityID = entityID == null ? "" : entityID;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof EntityLockKey && entityID.equals(((EntityLockKey) obj).entityID);
    }

    @Override
    public int hashCode() {
        return entityID.hashCode();
    }
}