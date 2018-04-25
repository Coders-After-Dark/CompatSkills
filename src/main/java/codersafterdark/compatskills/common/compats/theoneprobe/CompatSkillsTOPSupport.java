package codersafterdark.compatskills.common.compats.theoneprobe;

import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class CompatSkillsTOPSupport implements Function<ITheOneProbe, Void> {

    public static ITheOneProbe probe;
    @Nullable
    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        probe = iTheOneProbe;
        probe.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return CompatSkillConstants.MOD_ID;
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
                ItemStack stack = data.getPickBlock();
                RequirementHolder holder = LevelLockHandler.getSkillLock(stack);
                if (holder.isRealLock()){
                    List<Requirement> requirements = holder.getRequirements();
                    PlayerData playerData = PlayerDataHandler.get(player);
                    probeInfo.text(TextFormatting.GRAY + "Level Locks:");
                    for (Requirement req : requirements){
                        probeInfo.text(req.getToolTip(playerData));
                    }

                }
            }
        });
        return null;
    }
}
