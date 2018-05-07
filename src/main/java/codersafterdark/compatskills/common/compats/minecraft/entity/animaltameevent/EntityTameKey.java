package codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent;

import codersafterdark.compatskills.common.compats.minecraft.entity.EntityLockKey;
import net.minecraft.entity.Entity;

public class EntityTameKey extends EntityLockKey {
    public EntityTameKey(String entityID) {
        super(entityID);
    }

    public EntityTameKey(Entity entity) {
        this(getEntityID(entity));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof EntityTameKey;
    }
}