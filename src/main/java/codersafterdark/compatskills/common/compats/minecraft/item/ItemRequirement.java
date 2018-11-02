package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.reskillable.api.data.*;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class ItemRequirement extends Requirement {
    //TODO: maybe make an API endpoint in Reskillable to somehow access the types of things that can be created from ItemStack
    //TODO cont: in case there are custom ones (like the emc lock in CompatSkills), would need a more generic way of creating requirement
    private final NBTLockKey key;

    public ItemRequirement(NBTLockKey key) {
        this.key = key;

        //TODO make the combined information look nicer in the formatting
        String displayName = "";
        if (key instanceof ItemInfo) {
            ItemInfo info = (ItemInfo) key;
            ItemStack stack = new ItemStack(info.getItem(), 1, info.getMetadata());
            stack.setTagCompound(key.getTag());//Needed in case the tag has a custom item name stored
            displayName += stack.getDisplayName();
        } else if (key instanceof ModLockKey) {
            displayName += "From Mod: " + ((ModLockKey) key).getModName();
        }// else if (key instanceof GenericNBTLockKey) //No specific check needed because it just needs to show what the nbt tag must be
        if (key.getTag() != null) {
            displayName += " With NBT Tag: " + key.getTag().toString();//Maybe format NBT slightly better
        }
        this.tooltip = TextFormatting.GRAY + " - " + new TextComponentTranslation("compatskills.misc.requirements.itemRequirementFormat", "%s", displayName.trim()).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        return itemMatches(player.getHeldItemMainhand()) || itemMatches(player.getHeldItemOffhand());
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o instanceof ItemRequirement) {
            ItemRequirement other = (ItemRequirement) o;
            if (key.equals(other.key)) {
                return RequirementComparision.EQUAL_TO;
            } else {
                if (key instanceof ItemInfo) {
                    if (other.key instanceof ItemInfo) {
                        if (key.getNotFuzzy().equals(other.key.getNotFuzzy())) {
                            if (key.fuzzyEquals(other.key)) {
                                return RequirementComparision.GREATER_THAN;
                            } else if (other.key.fuzzyEquals(key)) {
                                return RequirementComparision.LESS_THAN;
                            }
                        }
                    } else if (other.key instanceof ModLockKey) {
                        ResourceLocation registryName = ((ItemInfo) key).getItem().getRegistryName();
                        if (registryName != null && registryName.getNamespace().equals(((ModLockKey) other.key).getModName()) && key.fuzzyEquals(other.key)) {
                            //Does not need to check for LESS_THAN because we are at an item level lock compared to a mod level one
                            return RequirementComparision.GREATER_THAN;
                        }
                    } else if (other.key instanceof GenericNBTLockKey && key.fuzzyEquals(other.key)) {
                        return RequirementComparision.GREATER_THAN;
                    }
                } else if (key instanceof ModLockKey) {
                    if (other.key instanceof ItemInfo) {
                        ResourceLocation registryName = ((ItemInfo) other.key).getItem().getRegistryName();
                        if (registryName != null && ((ModLockKey) key).getModName().equals(registryName.getNamespace()) && key.fuzzyEquals(other.key)) {
                            //Does not need to check for GREATER_THAN because we are at a mod level lock compared to an item one
                            return RequirementComparision.LESS_THAN;
                        }
                    } else if (other.key instanceof ModLockKey) {
                        if (key.getNotFuzzy().equals(other.key.getNotFuzzy())) {
                            if (key.fuzzyEquals(other.key)) {
                                return RequirementComparision.GREATER_THAN;
                            } else if (other.key.fuzzyEquals(key)) {
                                return RequirementComparision.LESS_THAN;
                            }
                        }
                    } else if (other.key instanceof GenericNBTLockKey && key.fuzzyEquals(other.key)) {
                        //If key has all the nbt that other has in addition to being more restrictive
                        //Does not need to check for LESS_THAN because we are at an mod level lock compared to a general lock
                        return RequirementComparision.GREATER_THAN;
                    }
                } else if (key instanceof GenericNBTLockKey) {
                    if (other.key instanceof GenericNBTLockKey && key.fuzzyEquals(other.key)) {
                        //If the other one is generic there is a chance our current one is more restrictive
                        return RequirementComparision.GREATER_THAN;
                    } else if (other.key.fuzzyEquals(key)) {
                        //Generic see if there is a more restrictive requirement in the other one
                        return RequirementComparision.LESS_THAN;
                    }
                }
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }

    private boolean itemMatches(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        NBTLockKey fullKey = null;
        if (key instanceof ItemInfo) {
            fullKey = new ItemInfo(stack);
        } else if (key instanceof ModLockKey) {
            fullKey = new ModLockKey(stack);
        } else if (key instanceof GenericNBTLockKey) {
            fullKey = new GenericNBTLockKey(stack);
        }
        if (fullKey != null) {
            //Needs to make sure the base keys are the same
            LockKey notFuzzy = key.getNotFuzzy();
            LockKey fullNotFuzzy = fullKey.getNotFuzzy();
            if (notFuzzy == null) {
                return fullNotFuzzy == null && fullKey.fuzzyEquals(key);
            }
            return notFuzzy.equals(fullNotFuzzy) && fullKey.fuzzyEquals(key);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof ItemRequirement && key.equals(((ItemRequirement) o).key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}