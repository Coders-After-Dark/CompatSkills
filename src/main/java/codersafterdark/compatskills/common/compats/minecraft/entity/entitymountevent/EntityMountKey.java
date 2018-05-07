package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.compatskills.common.compats.minecraft.entity.EntityLockKey;
import net.minecraft.entity.Entity;

public class EntityMountKey extends EntityLockKey {
    public EntityMountKey(String entityID) {
        super(entityID);
    }

    public EntityMountKey(Entity entity) {
        this(getEntityID(entity));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof EntityMountKey;
    }
}