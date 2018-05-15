package codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent;

import codersafterdark.compatskills.common.compats.minecraft.entity.EntityLockKey;
import net.minecraft.entity.Entity;

public class EntityDamageKey extends EntityLockKey {
    public EntityDamageKey(String entityID) {
        super(entityID);
    }

    public EntityDamageKey(Entity entity) {
        this(getEntityID(entity));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof EntityDamageKey;
    }
}