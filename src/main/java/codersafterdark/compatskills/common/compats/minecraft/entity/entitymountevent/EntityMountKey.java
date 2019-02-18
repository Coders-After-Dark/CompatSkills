package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.compatskills.common.compats.minecraft.entity.EntityLockKey;
import codersafterdark.compatskills.utils.Utils;
import net.minecraft.entity.Entity;

public class EntityMountKey extends EntityLockKey {
    public EntityMountKey(String entityID) {
        super(entityID);
    }

    public EntityMountKey(Entity entity) {
        this(Utils.getEntityID(entity));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof EntityMountKey;
    }
}