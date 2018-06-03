package codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class ToolHarvestLock implements FuzzyLockKey {
    private final int harvestLevel;

    public ToolHarvestLock(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public ToolHarvestLock(ItemStack stack) {
        if (stack.isEmpty()) {
            this.harvestLevel = 0;
            return;
        }
        int highestLevel = 0;
        Item item = stack.getItem();
        for (String toolClass : item.getToolClasses(stack)) {
            int level = item.getHarvestLevel(stack, toolClass, null, null);
            if (level > highestLevel) {
                highestLevel = level;
            }
        }
        this.harvestLevel = highestLevel;
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof ToolHarvestLock && harvestLevel >= ((ToolHarvestLock) o).harvestLevel;
    }

    @Override
    public boolean isNotFuzzy() {
        return false;
    }

    @Override
    public LockKey getNotFuzzy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof ToolHarvestLock && harvestLevel == ((ToolHarvestLock) o).harvestLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(harvestLevel);
    }
}