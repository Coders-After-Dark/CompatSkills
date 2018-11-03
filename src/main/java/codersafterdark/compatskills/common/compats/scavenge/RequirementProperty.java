package codersafterdark.compatskills.common.compats.scavenge;

import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.requirement.Requirement;
import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scavenge.api.block.IResourceCondition;
import scavenge.api.block.impl.BaseResourceProperty;

public class RequirementProperty extends BaseResourceProperty implements IResourceCondition {
    private Requirement requirement;

    public RequirementProperty(JsonObject obj) {
        super(obj, "reskillable_requirement");
        String req = obj.get("requirement").getAsString();
        this.requirement = ReskillableAPI.getInstance().getRequirementRegistry().getRequirement(req);
        if (this.requirement == null) {
            throw new RuntimeException("Requirement [" + req + "] not found.");
        } else {
            this.setJEIInfo(requirement.getToolTip(null));
        }
    }

    public boolean canInteract(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean leftClick, EnumFacing side, String resourceID, boolean client) {
        PlayerData data = PlayerDataHandler.get(player);
        return data != null && data.requirementAchieved(this.requirement);
    }
}