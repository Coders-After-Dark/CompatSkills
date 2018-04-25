package codersafterdark.compatskills.common.compats.waila;

import codersafterdark.reskillable.api.data.*;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class CompatSkillsWailaDataProvider implements IWailaDataProvider {

    @Override
    @Nonnull
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (LevelLockHandler.getSkillLock(accessor.getStack()).isRealLock()){
            RequirementHolder requirementHolder = LevelLockHandler.getSkillLock(accessor.getStack());
            List<Requirement> requirements = requirementHolder.getRequirements();
            EntityPlayer player = accessor.getPlayer();
            PlayerData playerData = PlayerDataHandler.get(player);
            for (Requirement req : requirements){
                currenttip.add(req.getToolTip(playerData));
            }
        }
        return currenttip;
    }
}
