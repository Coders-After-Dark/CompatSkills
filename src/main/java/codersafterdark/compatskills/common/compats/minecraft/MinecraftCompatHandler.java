package codersafterdark.compatskills.common.compats.minecraft;

import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockHandler;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirement;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.AnimalTameEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent.EntityDamageEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.item.ItemRequirement;
import codersafterdark.compatskills.common.compats.minecraft.item.OreDictRequirement;
import codersafterdark.compatskills.common.compats.minecraft.item.ParentOreDictLock;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityCommand;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityEventHandler;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.GenericNBTLockKey;
import codersafterdark.reskillable.api.data.ItemInfo;
import codersafterdark.reskillable.api.data.ModLockKey;
import codersafterdark.reskillable.api.data.NBTLockKey;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class MinecraftCompatHandler {
    public static void setup() {
        LevelLockHandler.registerLockKey(ItemStack.class, ParentOreDictLock.class);

        AnimalTameEventHandler tameEventHandler = new AnimalTameEventHandler();
        EntityMountEventHandler entityMountEventHandler = new EntityMountEventHandler();
        DimensionLockHandler dimensionLockHandler = new DimensionLockHandler();
        TileEntityEventHandler tileEntityHandler = new TileEntityEventHandler();
        EntityDamageEventHandler entityDamageHandler = new EntityDamageEventHandler();
        MinecraftForge.EVENT_BUS.register(tameEventHandler);
        MinecraftForge.EVENT_BUS.register(entityMountEventHandler);
        MinecraftForge.EVENT_BUS.register(dimensionLockHandler);
        MinecraftForge.EVENT_BUS.register(tileEntityHandler);
        MinecraftForge.EVENT_BUS.register(entityDamageHandler);
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("dim", input -> {
            try {
                return new DimensionRequirement(Integer.parseInt(input));
            } catch (NumberFormatException ignored) {
            }
            return null;
        });
        registry.addRequirementHandler("!dim", input -> registry.getRequirement("not|dim|" + input));
        registry.addRequirementHandler("ore", input -> {
            if (input == null) {
                return null;
            }
            String[] inputInfo = input.split("\\|");
            //(modid,empty, or modid:item:optional metadata)|nbt as json
            String type = inputInfo[0]; //mod, generic, or item
            NBTTagCompound nbt = null;
            if (inputInfo.length > 1) {
                String nbtString = input.substring(type.length() + 1).trim();
                try {
                    nbt = JsonToNBT.getTagFromJson(nbtString);
                } catch (NBTException e) {
                    //Invalid NBT
                    return null;
                }
            }
            return OreDictionary.doesOreNameExist(inputInfo[0]) ? new OreDictRequirement(inputInfo[0], nbt) : null;
        });
        registry.addRequirementHandler("stack", input -> {
            String[] inputInfo = input.split("\\|");
            //(modid,empty, or modid:item:optional metadata)|nbt as json
            String type = inputInfo[0]; //mod, generic, or item
            NBTTagCompound nbt = null;
            if (inputInfo.length > 1) {
                String nbtString = input.substring(type.length() + 1).trim();
                try {
                    nbt = JsonToNBT.getTagFromJson(nbtString);
                } catch (NBTException e) {
                    //Invalid NBT
                    return null;
                }
            }
            NBTLockKey key;
            type = type.trim();
            if (type.isEmpty()) {
                if (nbt == null) {
                    return null;
                }
                key = new GenericNBTLockKey(nbt);
            } else {
                String[] itemParts = type.split(":");
                if (itemParts.length == 1) {//is a modid
                    //TODO should it check if a mod is loaded, and would this cause issues in when requirements are read from config
                    key = new ModLockKey(type, nbt);
                } else {
                    int metadata = 0;
                    if (itemParts.length > 2) {
                        String meta = itemParts[2];
                        try {
                            if (meta.equals("*")) {
                                metadata = OreDictionary.WILDCARD_VALUE;
                            } else {
                                metadata = Integer.parseInt(meta);
                            }
                            type = itemParts[0] + ':' + itemParts[1];
                        } catch (NumberFormatException ignored) {
                            //Do nothing if the meta is not a valid number or wildcard (Maybe it somehow is part of the item name)
                        }
                    }
                    Item item = Item.getByNameOrId(type);
                    if (item == null) {
                        return null;
                    }
                    key = new ItemInfo(item, metadata, nbt);
                }
            }
            return new ItemRequirement(key);
        });

        CTChatCommand.registerCommand(new TileEntityCommand());
    }
}
