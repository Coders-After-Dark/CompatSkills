package codersafterdark.compatskills.common.compats.oreexcavator.filter;

import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import oreexcavation.events.IExcavateFilter;
import oreexcavation.handlers.MiningAgent;

public class ExcavateRequirementFilter implements IExcavateFilter {
    @Override
    public boolean canHarvest(EntityPlayerMP entityPlayerMP, MiningAgent miningAgent, BlockPos blockPos) {
        World world = entityPlayerMP.getEntityWorld();
        IBlockState state = world.getBlockState(blockPos);
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block, 1, state.getBlock().getMetaFromState(state));
        if (stack.isEmpty()) {
            stack = block.getItem(world, blockPos, state);
        }
        if (block.hasTileEntity(state)) {
            TileEntity te = world.getTileEntity(blockPos);
            if (te != null && !te.isInvalid()) {
                stack.setTagCompound(te.writeToNBT(new NBTTagCompound()));
            }
        }
        return LevelLockHandler.canPlayerUseItem(entityPlayerMP, stack);
    }
}
