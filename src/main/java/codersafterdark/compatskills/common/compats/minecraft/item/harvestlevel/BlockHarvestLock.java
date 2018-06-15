package codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class BlockHarvestLock implements FuzzyLockKey {
    private final int harvestLevel;

    public BlockHarvestLock(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public BlockHarvestLock(ItemStack stack) {
        Block block = Block.getBlockFromItem(stack.getItem());
        this.harvestLevel = block == Blocks.AIR ? 0 : block.getHarvestLevel(block.getDefaultState());
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof BlockHarvestLock && harvestLevel >= ((BlockHarvestLock) o).harvestLevel;
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
        return o == this || o instanceof BlockHarvestLock && harvestLevel == ((BlockHarvestLock) o).harvestLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(harvestLevel);
    }
}