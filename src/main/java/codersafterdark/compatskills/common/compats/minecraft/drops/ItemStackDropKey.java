package codersafterdark.compatskills.common.compats.minecraft.drops;

import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.ItemStack;

public class ItemStackDropKey implements LockKey {
    private final ItemStack stack;

    protected ItemStackDropKey(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ItemStackDropKey && stack.equals(((ItemStackDropKey) obj).stack);
    }

    @Override
    public int hashCode() {
        return stack.hashCode();
    }
}
