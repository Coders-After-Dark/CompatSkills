package codersafterdark.compatskills.common.compats.minecraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockHandler;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionlocks.DimensionLockKey;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirement;
import codersafterdark.compatskills.common.compats.minecraft.dimension.dimensionrequirement.DimensionRequirementHandler;
import codersafterdark.compatskills.common.compats.minecraft.drops.BlockDropsHandler;
import codersafterdark.compatskills.common.compats.minecraft.drops.ItemStackDropKey;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.AnimalTameEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent.EntityTameKey;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent.EntityDamageEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent.EntityDamageKey;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent.EntityMountKey;
import codersafterdark.compatskills.common.compats.minecraft.health.HealthChangeHandler;
import codersafterdark.compatskills.common.compats.minecraft.health.HealthRequirement;
import codersafterdark.compatskills.common.compats.minecraft.health.HeartRequirement;
import codersafterdark.compatskills.common.compats.minecraft.item.*;
import codersafterdark.compatskills.common.compats.minecraft.item.armor.ArmorLockKey;
import codersafterdark.compatskills.common.compats.minecraft.item.food.HungerLockKey;
import codersafterdark.compatskills.common.compats.minecraft.item.food.SaturationLockKey;
import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.BlockHarvestLock;
import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.HarvestLevelRequirement;
import codersafterdark.compatskills.common.compats.minecraft.item.harvestlevel.ToolHarvestLock;
import codersafterdark.compatskills.common.compats.minecraft.item.weapon.AttackDamageLockKey;
import codersafterdark.compatskills.common.compats.minecraft.looking.LookingAtBlockRequirement;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityCommand;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityEventHandler;
import codersafterdark.compatskills.common.compats.minecraft.tileentity.TileEntityLockKey;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashSet;
import java.util.Set;

public class MinecraftCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    private static Set<Class<? extends LockKey>> lockTypes = new HashSet<>();
    private static boolean tile, damage, mount, tame, dimension, drops;

    @Override
    public void preInit() {
        ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new DimensionRequirementHandler());
        MinecraftForge.EVENT_BUS.register(new ItemChangeHandler());
        MinecraftForge.EVENT_BUS.register(new HealthChangeHandler());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("sneaking", input -> new SneakRequirement());
        registry.addRequirementHandler("hearts", HeartRequirement::fromString);
        registry.addRequirementHandler("health", HealthRequirement::fromString);
        registry.addRequirementHandler("harvest", HarvestLevelRequirement::fromString);
        registry.addRequirementHandler("dim", DimensionRequirement::fromString);
        registry.addRequirementHandler("!dim", input -> registry.getRequirement("not|dim|" + input));
        registry.addRequirementHandler("ore", OreDictRequirement::fromString);
        registry.addRequirementHandler("stack", ItemRequirement::fromString);
        registry.addRequirementHandler("looking_at", LookingAtBlockRequirement::fromString);
        //TODO: Implement a way to check what entity the player is looking at then uncomment this line
        //registry.addRequirementHandler("looking_at_entity", LookingAtEntityRequirement::fromString);
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new TileEntityCommand());
        }
    }

    public static void addMCLock(LockKey key, RequirementHolder holder) {
        if (key instanceof ItemStackDropKey) {
            registerItemLock(ItemStackDropKey.class);
            if (!drops) {
                MinecraftForge.EVENT_BUS.register(new BlockDropsHandler());
                drops = true;
            }
        } else if (key instanceof OreDictLock) {
            registerItemLock(ParentOreDictLock.class);
        } else if (key instanceof ToolHarvestLock) {
            registerItemLock(ToolHarvestLock.class);
        } else if (key instanceof BlockHarvestLock) {
            registerItemLock(BlockHarvestLock.class);
        } else if (key instanceof ArmorLockKey) {
            registerItemLock(ArmorLockKey.class);
        } else if (key instanceof AttackDamageLockKey) {
            registerItemLock(AttackDamageLockKey.class);
        } else if (key instanceof HungerLockKey) {
            registerItemLock(HungerLockKey.class);
        } else if (key instanceof SaturationLockKey) {
            registerItemLock(SaturationLockKey.class);
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