package codersafterdark.compatskills.common.compats.minecraft.item.food;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class SaturationLockKey implements FuzzyLockKey {
    private final float saturation;

    public SaturationLockKey(float saturation) {
        this.saturation = saturation;
    }

    public SaturationLockKey(ItemStack stack) {
        if (stack.getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) stack.getItem();
            saturation = food.getHealAmount(stack) * food.getSaturationModifier(stack) * 2f;
        } else {
            saturation = -1;
        }
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof SaturationLockKey && saturation >= ((SaturationLockKey) o).saturation;
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
        return Objects.hash(saturation);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof SaturationLockKey && saturation == ((SaturationLockKey) o).saturation;
    }
}