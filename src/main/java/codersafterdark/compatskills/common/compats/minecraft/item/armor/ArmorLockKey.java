package codersafterdark.compatskills.common.compats.minecraft.item.armor;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class ArmorLockKey implements FuzzyLockKey {//TODO: Eventually support armor toughness
    private final int armor;

    public ArmorLockKey(int armor) {
        this.armor = armor;
    }

    public ArmorLockKey(ItemStack stack) {
        if (stack.isEmpty()) {
            this.armor = 0;
            return;
        }
        Item item = stack.getItem();
        if (item instanceof ItemArmor) {
            this.armor = ((ItemArmor) item).damageReduceAmount;
        } else {
            this.armor = 0;
        }
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof ArmorLockKey && armor >= ((ArmorLockKey) o).armor;
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
        return o == this || o instanceof ArmorLockKey && armor == ((ArmorLockKey) o).armor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(armor);
    }
}