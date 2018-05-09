package codersafterdark.compatskills.common.compats.minecraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockHandler;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirement;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.AnimalTameEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityCommand;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityEventHandler;
import codersafterdark.compatskills.common.invertedrequirements.InvertedDimension;
import codersafterdark.reskillable.api.ReskillableAPI;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.util.Set;

public class MinecraftCompatHandler {
    private static RegistrySimple<ResourceLocation, Class <? extends TileEntity >> tileRegistry;//TODO make this not public and do things
    private static final String REGISTRY_NAME = "field_190562_f";

    public static void setup() {
        hookTileRegistry();

        AnimalTameEventHandler tameEventHandler = new AnimalTameEventHandler();
        EntityMountEventHandler entityMountEventHandler = new EntityMountEventHandler();
        DimensionLockHandler dimensionLockHandler = new DimensionLockHandler();
        TileEntityEventHandler tileEntityHandler = new TileEntityEventHandler();
        MinecraftForge.EVENT_BUS.register(tameEventHandler);
        MinecraftForge.EVENT_BUS.register(entityMountEventHandler);
        MinecraftForge.EVENT_BUS.register(dimensionLockHandler);
        MinecraftForge.EVENT_BUS.register(tileEntityHandler);
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

        CTChatCommand.registerCommand(new TileEntityCommand() {
            @Override
            protected Set<ResourceLocation> getKeys() {
                return tileRegistry == null ? null : tileRegistry.getKeys();
            }
        });
    }

    private static void hookTileRegistry() {
        if (tileRegistry == null) {
            try {
                Field teRegField = TileEntity.class.getDeclaredField(REGISTRY_NAME);
                teRegField.setAccessible(true);
                Object teRegistry = teRegField.get(null);
                if (teRegistry instanceof RegistrySimple) {
                    tileRegistry = (RegistrySimple<ResourceLocation, Class<? extends TileEntity>>) teRegistry;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                CompatSkills.logger.log(Level.ERROR, "Failed to hook into Tile Entity Registry.");
            }
        }
    }

    public static boolean teRegistryContains(ResourceLocation location) {
        return tileRegistry != null && tileRegistry.containsKey(location);
    }
}
