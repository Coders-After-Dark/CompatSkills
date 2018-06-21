package codersafterdark.compatskills.common.compats.minecraft;

import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockHandler;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockKey;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirement;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirementHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.AnimalTameEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.EntityTameKey;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent.EntityDamageEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent.EntityDamageKey;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountKey;
import codersafterdark.compatskills.common.compats.minecraft.item.*;
import codersafterdark.compatskills.common.compats.minecraft.item.armor.ArmorLockKey;
import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.BlockHarvestLock;
import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.HarvestLevelRequirement;
import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.ToolHarvestLock;
import codersafterdark.compatskills.common.compats.minecraft.item.weapon.AttackDamageLockKey;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityCommand;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityLockKey;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.*;
import codersafterdark.reskillable.api.requirement.RequirementException;
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

import java.util.HashSet;
import java.util.Set;

public class MinecraftCompatHandler {
    private static Set<Class<? extends LockKey>> lockTypes = new HashSet<>();
    private static boolean tile, damage, mount, tame, dimension;

    public static void setup() {
        MinecraftForge.EVENT_BUS.register(new DimensionRequirementHandler());
        MinecraftForge.EVENT_BUS.register(new ItemChangeHandler());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("harvest", input -> {
            try {
                return new HarvestLevelRequirement(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                throw new RequirementException("Invalid harvest level '" + input + "'.");
            }
        });
        registry.addRequirementHandler("dim", input -> {
            try {
                return new DimensionRequirement(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                throw new RequirementException("Invalid dimension id '" + input + "'.");
            }
        });
        registry.addRequirementHandler("!dim", input -> registry.getRequirement("not|dim|" + input));
        registry.addRequirementHandler("ore", input -> {
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
                    throw new RequirementException("Invalid NBT JSON '" + nbtString + "'.");
                }
            }
            String ore = inputInfo[0];
            if (!OreDictionary.doesOreNameExist(ore)) {
                throw new RequirementException("Ore dictionary entry '" + ore + "' not found.");
            }
            return new OreDictRequirement(ore, nbt);
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
                    throw new RequirementException("Invalid NBT JSON '" + nbtString + "'.");
                }
            }
            NBTLockKey key;
            type = type.trim();
            if (type.isEmpty()) {
                if (nbt == null) {
                    throw new RequirementException("Invalid Item Requirement format. No input data found.");
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
                        throw new RequirementException("No Item found matching: '" + type + "'.");
                    }
                    key = new ItemInfo(item, metadata, nbt);
                }
            }
            return new ItemRequirement(key);
        });

        CTChatCommand.registerCommand(new TileEntityCommand());
    }

    public static void addMCLock(LockKey key, RequirementHolder holder) {
        if (key instanceof OreDictLock) {
            registerItemLock(ParentOreDictLock.class);
        } else if (key instanceof ToolHarvestLock) {
            registerItemLock(ToolHarvestLock.class);
        } else if (key instanceof BlockHarvestLock) {
            registerItemLock(BlockHarvestLock.class);
        } else if (key instanceof ArmorLockKey) {
            registerItemLock(ArmorLockKey.class);
        } else if (key instanceof AttackDamageLockKey) {
            registerItemLock(AttackDamageLockKey.class);
        } else if (key instanceof TileEntityLockKey) {
            if (!tile) {
                MinecraftForge.EVENT_BUS.register(new TileEntityEventHandler());
                tile = true;
            }
        } else if (key instanceof EntityDamageKey) {
            if (!damage) {
                MinecraftForge.EVENT_BUS.register(new EntityDamageEventHandler());
                damage = true;
            }
        } else if (key instanceof EntityMountKey) {
            if (!mount) {
                MinecraftForge.EVENT_BUS.register(new EntityMountEventHandler());
                mount = true;
            }
        } else if (key instanceof EntityTameKey) {
            if (!tame) {
                MinecraftForge.EVENT_BUS.register(new AnimalTameEventHandler());
                tame = true;
            }
        } else if (key instanceof DimensionLockKey) {
            if (!dimension) {
                MinecraftForge.EVENT_BUS.register(new DimensionLockHandler());
                dimension = true;
            }
        }
        LevelLockHandler.addLockByKey(key, holder);
    }

    private static void registerItemLock(Class<? extends LockKey> itemLockType) {
        if (!lockTypes.contains(itemLockType)) {
            LevelLockHandler.registerLockKey(ItemStack.class, itemLockType);
            lockTypes.add(itemLockType);
        }
    }
}