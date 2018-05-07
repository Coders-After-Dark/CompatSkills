package codersafterdark.compatskills.common.compats.minecraft.entity;

import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public abstract class EntityLockKey implements LockKey {
    private final String entityID;

    protected EntityLockKey (String entityID) {
        this.entityID = entityID == null ? "" : entityID;
    }

    //returns no_registry_name like CraftTweaker does so that it can be marked as equal
    protected static String getEntityID(Entity entity) {
        for (EntityEntry entry: ForgeRegistries.ENTITIES.getValuesCollection()) {
            if (entry.getEntityClass() == entity.getClass()) {
                return entry.getRegistryName() != null ? entry.getRegistryName().toString() : "no_registry_name";
            }
        }
        return "no_registry_name";
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
