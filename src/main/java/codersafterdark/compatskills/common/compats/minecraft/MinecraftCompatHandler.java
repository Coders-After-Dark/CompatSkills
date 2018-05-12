package codersafterdark.compatskills.common.compats.minecraft;

import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockHandler;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirement;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.AnimalTameEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.item.ItemRequirement;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityCommand;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityEventHandler;
import codersafterdark.compatskills.common.invertedrequirements.InvertedDimension;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class MinecraftCompatHandler {
    public static void setup() {
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
        ReskillableAPI.getInstance().getRequirementRegistry().addRequirementHandler("stack", input -> {
            try {
                if (input.length() == 2){
                    String name = input.toLowerCase();
                    String[] parts = name.split(":");
                    int metadata = 0;
                    if (parts.length > 2){
                        String meta = parts[2];
                        try {
                            if (meta.equals("*")){
                                metadata = OreDictionary.WILDCARD_VALUE;
                            } else {
                                metadata = Integer.parseInt(meta);
                            }
                            name = parts[0] + ":" + parts[1];
                        } catch (NumberFormatException ignored) {

                        }
                        if (Item.getByNameOrId(name) != null){
                            new ItemRequirement(new ItemStack(Item.getByNameOrId(name), 1, metadata));
                        }
                    }
                }
            } catch (TypeNotPresentException ignored){
            }
            return null;
        });

        CTChatCommand.registerCommand(new TileEntityCommand());
    }
}
