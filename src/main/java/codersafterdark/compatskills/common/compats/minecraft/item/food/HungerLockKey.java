package codersafterdark.compatskills.common.compats.minecraft.item.food;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class HungerLockKey implements FuzzyLockKey {
    private final int hunger;

    public HungerLockKey(int hunger) {
        this.hunger = hunger;
    }

    public HungerLockKey(ItemStack stack) {
        this(stack.getItem() instanceof ItemFood ? ((ItemFood) stack.getItem()).getHealAmount(stack) : -1);
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof HungerLockKey && hunger >= ((HungerLockKey) o).hunger;
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
    public int hashCode() {
        return Objects.hash(hunger);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof HungerLockKey && hunger == ((HungerLockKey) o).hunger;
    }
}