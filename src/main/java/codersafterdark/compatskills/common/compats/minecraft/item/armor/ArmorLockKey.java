package codersafterdark.compatskills.common.compats.minecraft.item.armor;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Objects;

public class ArmorLockKey implements FuzzyLockKey {//TODO: Eventually support armor toughness
    private final double armor;

    public ArmorLockKey(double armor) {
        this.armor = armor;
    }

    public ArmorLockKey(ItemStack stack) {
        if (stack.isEmpty()) {
            this.armor = 0;
            return;
        }
        Item item = stack.getItem();
        if (item instanceof ItemArmor) {
            ItemArmor itemArmor = (ItemArmor) item;
            Multimap<String, AttributeModifier> attributeModifiers = itemArmor.getAttributeModifiers(itemArmor.armorType, stack);
            Collection<AttributeModifier> protection = attributeModifiers.get(SharedMonsterAttributes.ARMOR.getName());
            if (protection.isEmpty()) {
                this.armor = itemArmor.damageReduceAmount;
            } else {
                this.armor = itemArmor.damageReduceAmount + protection.stream().findFirst().map(AttributeModifier::getAmount).orElse(0D);
            }
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