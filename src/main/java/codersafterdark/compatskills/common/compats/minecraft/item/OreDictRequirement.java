package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.reskillable.api.data.GenericNBTLockKey;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Objects;

public class OreDictRequirement extends Requirement {
    private final NBTTagCompound tag;
    private final int oreEntry;

    public OreDictRequirement(String oreDictEntry, NBTTagCompound tag) {
        this.oreEntry = OreDictionary.getOreID(oreDictEntry); //Cache the value
        this.tag = tag;
        String name = oreDictEntry;
        if (tag != null) {
            name += " With NBT Tag: " + tag;//Maybe format NBT slightly better
        }
        this.tooltip = TextFormatting.GRAY + " - " + new TextComponentTranslation("compatskills.misc.requirements.oreDictRequirementFormat", "%s", name).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        return hasOreDict(player.getHeldItemMainhand()) || hasOreDict(player.getHeldItemOffhand());
    }

    private boolean hasOreDict(ItemStack stack) {
        if (stack.isEmpty() || Arrays.stream(OreDictionary.getOreIDs(stack)).noneMatch(oreID -> oreID == oreEntry)) {
            return false;
        }
        return new GenericNBTLockKey(stack.getTagCompound()).fuzzyEquals(new GenericNBTLockKey(tag));
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o instanceof OreDictRequirement) {
            OreDictRequirement other = (OreDictRequirement) o;
            if (oreEntry == other.oreEntry) {
                GenericNBTLockKey key = new GenericNBTLockKey(tag);
                GenericNBTLockKey otherKey = new GenericNBTLockKey(other.tag);
                if (key.fuzzyEquals(otherKey)) {
                    //If the other one is generic there is a chance our current one is more restrictive
                    return RequirementComparision.GREATER_THAN;
                } else if (otherKey.fuzzyEquals(key)) {
                    //Generic see if there is a more restrictive requirement in the other one
                    return RequirementComparision.LESS_THAN;
                }
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof OreDictRequirement) {
            OreDictRequirement oreReq = (OreDictRequirement) o;
            if (tag == null) {
                return oreEntry == oreReq.oreEntry && oreReq.tag == null;
            }
            return oreEntry == oreReq.oreEntry && tag.equals(oreReq.tag);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oreEntry, tag);
    }
}