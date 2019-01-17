package codersafterdark.compatskills.common.compats.oreexcavator.filter;

import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import oreexcavation.events.IExcavateFilter;
import oreexcavation.handlers.MiningAgent;

public class ExcavateRequirementFilter implements IExcavateFilter {
    @Override
    public boolean canHarvest(EntityPlayerMP entityPlayerMP, MiningAgent miningAgent, BlockPos blockPos) {
        IBlockState state = entityPlayerMP.getEntityWorld().getBlockState(blockPos);
        return LevelLockHandler.canPlayerUseItem(entityPlayerMP, new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
    }
}
