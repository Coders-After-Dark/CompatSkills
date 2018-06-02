package codersafterdark.compatskills.common.compats.minecraft.item;

import codersafterdark.reskillable.api.data.ParentLockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

//This lock should never be created directly, but only be used by the automated checking for items so that we can call the getSubRequirements on it
//The methods for creating it directly are for in case we ever change our mind about this. (Unlikely)
public class ParentOreDictLock implements ParentLockKey {
    private final NBTTagCompound tag;
    private final int[] oreIDs;

    public ParentOreDictLock(ItemStack stack) {
        this(OreDictionary.getOreIDs(stack), stack.getTagCompound());
    }

    public ParentOreDictLock(int[] oreIDs) {
        this(oreIDs, null);
    }

    public ParentOreDictLock(int[] oreIDs, NBTTagCompound tag) {
        this.oreIDs = oreIDs;
        this.tag = tag;
    }

    @Override
    public RequirementHolder getSubRequirements() {
        List<RequirementHolder> holders = new ArrayList<>();
        for (int id : oreIDs) {
            RequirementHolder holder = LevelLockHandler.getLockByFuzzyKey(new OreDictLock(OreDictionary.getOreName(id), tag));
            if (!holder.equals(LevelLockHandler.EMPTY_LOCK)) {
                holders.add(holder);
            }
        }
        return holders.isEmpty() ? LevelLockHandler.EMPTY_LOCK : new RequirementHolder(holders.toArray(new RequirementHolder[0]));
    }
}