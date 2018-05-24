package codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks;

import codersafterdark.reskillable.api.data.ParentLockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IToolMod;

public class MaterialLockKey implements ParentLockKey {
    private final Material material;

    public MaterialLockKey(Material material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MaterialLockKey) {
            MaterialLockKey other = (MaterialLockKey) o;
            return material == null ? other.material == null : material.equals(other.material);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return material == null ? super.hashCode() : material.hashCode();
    }

    @Override
    public RequirementHolder getSubRequirements() {
        return LevelLockHandler.getLocks(IToolMod.class, material.getDefaultTraits().toArray(new IToolMod[0]));
    }
}
