package codersafterdark.compatskills.common.compats.minecraft.item.weapon;

import codersafterdark.reskillable.api.data.FuzzyLockKey;
import codersafterdark.reskillable.api.data.LockKey;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Objects;

public class AttackDamageLockKey implements FuzzyLockKey {
    private final double attackDamage;

    public AttackDamageLockKey(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public AttackDamageLockKey(ItemStack stack) {
        if (stack.isEmpty()) {
            this.attackDamage = 0;
            return;
        }
        Multimap<String, AttributeModifier> attributeModifiers = stack.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, stack);
        Collection<AttributeModifier> aSpeed = attributeModifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
        if (aSpeed.isEmpty()) {
            this.attackDamage = 0;
            return;
        }
        this.attackDamage = aSpeed.stream().findFirst().map(AttributeModifier::getAmount).orElse(0D);
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey o) {
        return o == this || o instanceof AttackDamageLockKey && attackDamage >= ((AttackDamageLockKey) o).attackDamage;
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
        return o == this || o instanceof AttackDamageLockKey && attackDamage == ((AttackDamageLockKey) o).attackDamage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackDamage);
    }
}