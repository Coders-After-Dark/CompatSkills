package codersafterdark.compatskills.common.compats.tinkersconstruct;

import codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks.ModifierLockKey;
import codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks.ToolTypeLockKey;
import codersafterdark.reskillable.api.data.ParentLockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ArrayList;
import java.util.List;

public class ToolWrapperLockKey implements ParentLockKey {
    private ItemStack stack;

    public ToolWrapperLockKey(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public RequirementHolder getSubRequirements() {
        Material mat = TinkerUtil.getMaterialFromStack(stack);
        if (!mat.equals(Material.UNKNOWN)) {
            return LevelLockHandler.getLocks(Material.class, mat);
        }

        List<RequirementHolder> holders = new ArrayList<>();
        RequirementHolder toolHolder = LevelLockHandler.getLockByKey(new ToolTypeLockKey(stack.getItem()));
        if (!toolHolder.equals(LevelLockHandler.EMPTY_LOCK)) {
            holders.add(toolHolder);
        }
        NBTTagList list = TagUtil.getBaseMaterialsTagList(stack);
        if (list.hasNoTags()) {
            return toolHolder;
        }
        TinkerUtil.getMaterialsFromTagList(list).stream().map(material -> LevelLockHandler.getLocks(Material.class, material)).forEach(holders::add);
        TinkerUtil.getTraitsOrdered(stack).stream().map(trait -> LevelLockHandler.getLockByKey(new ModifierLockKey(trait))).forEach(holders::add);
        return holders.isEmpty() ? LevelLockHandler.EMPTY_LOCK : new RequirementHolder(holders.toArray(new RequirementHolder[0]));
    }
}