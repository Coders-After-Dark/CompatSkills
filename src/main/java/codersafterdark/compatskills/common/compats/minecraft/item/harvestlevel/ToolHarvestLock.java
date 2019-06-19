package codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ToolHarvestLock implements FuzzyLockKey {
    private final Map<String, Integer> typeLevels = new HashMap<>();
    private final String toolType;
    private final int harvestLevel;

    public ToolHarvestLock(String toolType, int harvestLevel) {
        this.harvestLevel = harvestLevel;
        this.toolType = toolType;
        if (this.toolType != null) {
            this.typeLevels.put(this.toolType, this.harvestLevel);
        }
    }

    public ToolHarvestLock(ItemStack stack) {
        if (stack.isEmpty()) {
            this.harvestLevel = 0;
            this.toolType = null;
            return;
        }
        int highestLevel = 0;
        Item item = stack.getItem();
        for (String toolClass : item.getToolClasses(stack)) {
            int level = item.getHarvestLevel(stack, toolClass, null, null);
            if (level < 0) {
                continue;
            }
            this.typeLevels.put(toolClass, level);
            if (level > highestLevel) {
                highestLevel = level;
            }
        }
        this.harvestLevel = highestLevel;
        this.toolType = null;
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ToolHarvestLock) {
            ToolHarvestLock toolLock = (ToolHarvestLock) o;
            if (harvestLevel >= toolLock.harvestLevel) {
                if (toolLock.toolType == null) {
                    return toolType == null;
                }
                return toolLock.typeLevels.keySet().stream().noneMatch(s -> !typeLevels.containsKey(s) || typeLevels.get(s) < toolLock.typeLevels.get(s));
            }
        }
        return false;
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
        if (o == this) {
            return true;
        }
        if (o instanceof ToolHarvestLock) {
            ToolHarvestLock toolLock = (ToolHarvestLock) o;
            if (toolType == null) {
                return toolLock.toolType == null && harvestLevel == toolLock.harvestLevel;
            }
            return harvestLevel == toolLock.harvestLevel && toolType.equals(toolLock.toolType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolType, harvestLevel);
    }
}