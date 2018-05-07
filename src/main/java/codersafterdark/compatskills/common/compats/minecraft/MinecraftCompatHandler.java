package codersafterdark.compatskills.common.compats.minecraft;

import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockHandler;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirement;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.AnimalTameEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountEventHandler;
import codersafterdark.compatskills.common.invertedrequirements.InvertedDimension;
import codersafterdark.reskillable.api.ReskillableAPI;
import net.minecraftforge.common.MinecraftForge;

public class MinecraftCompatHandler {
    public static void setup(){
        AnimalTameEventHandler tameEventHandler = new AnimalTameEventHandler();
        EntityMountEventHandler entityMountEventHandler = new EntityMountEventHandler();
        DimensionLockHandler dimensionLockHandler = new DimensionLockHandler();
        MinecraftForge.EVENT_BUS.register(tameEventHandler);
        MinecraftForge.EVENT_BUS.register(entityMountEventHandler);
        MinecraftForge.EVENT_BUS.register(dimensionLockHandler);
        ReskillableAPI.getInstance().getRequirementRegistry().addRequirementHandler("dim", input -> {
            try {
                return new DimensionRequirement(Integer.parseInt(input));
            } catch (NumberFormatException ignored) {
            }
            return null;
        });
        ReskillableAPI.getInstance().getRequirementRegistry().addRequirementHandler("!dim", input -> {
            try {
                return new InvertedDimension(Integer.parseInt(input));
            } catch (NumberFormatException ignored) {
            }
            return null;
        });
    }
}
