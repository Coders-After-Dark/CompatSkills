package codersafterdark.compatskills.common.compats.reskillable.customcontent;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.unlockable.Trait;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.*;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.mc1120.events.handling.*;
import crafttweaker.util.IEventHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("mods.compatskills.TraitCreator")
public class CrTTrait extends Trait {

    @ZenProperty
    public IEventHandler<BlockHarvestDropsEvent> onBlockDrops;

    @ZenProperty
    public IEventHandler<crafttweaker.api.event.EnderTeleportEvent> onEnderTeleport;

    @ZenProperty
    public IEventHandler<PlayerTickEvent> onPlayerTick;

    @ZenProperty
    public IEventHandler<PlayerBreakSpeedEvent> getBreakSpeed;

    @ZenProperty
    public IEventHandler<EntityLivingDeathDropsEvent> onMobDrops;

    @ZenProperty
    public IEventHandler<EntityLivingHurtEvent> onAttackMob;

    @ZenProperty
    public IEventHandler<EntityLivingHurtEvent> onHurt;

    @ZenProperty
    public IEventHandler<PlayerRightClickBlockEvent> onRightClickBlock;

    @ZenProperty
    public IEventHandler<EntityLivingDeathEvent> onKillMob;

    @ZenProperty
    public IFormattedText name;

    @ZenProperty
    public IFormattedText description;

    public CrTTrait(ResourceLocation name, int x, int y, ResourceLocation skillName, int cost, String... requirements) {
        super(name, x, y, skillName, cost, requirements);
        ReskillableRegistries.UNLOCKABLES.register(this);

    }

    private static CrTTrait createTrait(ResourceLocation name, int x, int y, ResourceLocation skillName, int cost, String... requirements) {
        CrTTrait customTrait = new CrTTrait(name, x, y, skillName, cost, requirements);
        if (ReskillableRegistries.UNLOCKABLES.containsKey(name)) {
            Unlockable value = ReskillableRegistries.UNLOCKABLES.getValue(name);
            if (value instanceof CrTTrait) {
                String updated = "";
                //Update values that are not in sync
                if (customTrait.getX() != x) {
                    customTrait.unlockableConfig.setX(x);
                    updated += " - Updated X Pos: " + x;
                }
                if (customTrait.getY() != y) {
                    customTrait.unlockableConfig.setY(y);
                    updated += " - Updated Y Pos: " + y;
                }
                if (!skillName.equals(customTrait.getParentSkill().getRegistryName())) {
                    customTrait.setParentSkill(skillName);
                    updated += " - Updated Parent Skill: " + skillName;
                }
                if (customTrait.getCost() != cost) {
                    customTrait.unlockableConfig.setCost(cost);
                    updated += " - Updated Cost: " + cost;
                }
                StringBuilder reqBuilder = new StringBuilder();
                for (String string : requirements) {
                    reqBuilder.append(string);
                }
                RequirementHolder holder = RequirementHolder.fromStringList(requirements);
                if (!holder.equals(customTrait.getRequirements())) {
                    customTrait.unlockableConfig.setRequirementHolder(holder);
                    updated += " - Updated Requirements: " + reqBuilder;
                }
                if (!updated.isEmpty()) {
                    CraftTweakerAPI.logInfo("Loaded Trait: " + name + updated);
                } else {
                    CraftTweakerAPI.logInfo("Created or Loaded Trait: " + name + " -" + " With Pos: " + x + ", " + y + " - " + " With Cost: " + cost + " - Requirements: " + reqBuilder);
                }
            }
        }
        return customTrait;
    }

    @ZenMethod
    public static CrTTrait createTrait(String traitName, int x, int y, String skillLocation, int cost, String... requirements) {
        if (CheckMethods.checkString(traitName) & CheckMethods.checkIntX(x) & CheckMethods.checkIntY(y) & CheckMethods.checkParentSkillsString(skillLocation) & CheckMethods.checkInt(cost) & CheckMethods.checkStringArray(requirements)) {
            return createTrait(new ResourceLocation(CompatSkillConstants.MOD_ID, traitName), x, y, new ResourceLocation(skillLocation), cost, requirements);
        }
        return null;
    }

    @ZenMethod
    public static CrTTrait createTrait(String traitName, int x, int y, CrTSkill parentSkill, int cost, String... requirements) {
        if (CheckMethods.checkString(traitName) & CheckMethods.checkIntX(x) & CheckMethods.checkIntY(y) & CheckMethods.checkCrTSkillParent(parentSkill) & CheckMethods.checkInt(cost) & CheckMethods.checkStringArray(requirements)) {
            return createTrait(new ResourceLocation(CompatSkillConstants.MOD_ID, traitName), x, y, parentSkill.getRegistryName(), cost, requirements);
        }
        return null;
    }

    @ZenMethod
    public static CrTTrait createNewTrait(String traitLocation, int x, int y, String skillLocation, int cost, String... requirements) {
        if (CheckMethods.checkString(traitLocation) & CheckMethods.checkIntX(x) & CheckMethods.checkIntY(y) & CheckMethods.checkParentSkillsString(skillLocation) & CheckMethods.checkInt(cost) & CheckMethods.checkStringArray(requirements)) {
            return createTrait(new ResourceLocation(traitLocation), x, y, new ResourceLocation(skillLocation), cost, requirements);
        }
        return null;
    }

    @ZenMethod
    public static CrTTrait createNewTrait(String traitLocation, int x, int y, CrTSkill parentSkill, int cost, String... requirements) {
        if (CheckMethods.checkString(traitLocation) & CheckMethods.checkIntX(x) & CheckMethods.checkIntY(y) & CheckMethods.checkCrTSkillParent(parentSkill) & CheckMethods.checkInt(cost) & CheckMethods.checkStringArray(requirements)) {
            return createTrait(new ResourceLocation(traitLocation), x, y, parentSkill.getRegistryName(), cost, requirements);
        }
        return null;
    }

    @ZenGetter("enabled")
    @ZenMethod
    public boolean getEnabled() {
        return isEnabled();
    }

    @ZenSetter("enabled")
    @ZenMethod
    public void setEnabled(boolean enabled) {
        unlockableConfig.setEnabled(enabled);
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (onPlayerTick != null) {
            onPlayerTick.handle(new MCPlayerTickEvent(event));
        }
        super.onPlayerTick(event);
    }

    @Override
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        if (onBlockDrops != null) {
            onBlockDrops.handle(new MCBlockHarvestDropsEvent(event));
        }
        super.onBlockDrops(event);
    }

    @Override
    public void getBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (getBreakSpeed != null) {
            getBreakSpeed.handle(new MCPlayerBreakSpeedEvent(event));
        }
        super.getBreakSpeed(event);
    }

    @Override
    public void onMobDrops(LivingDropsEvent event) {
        if (onMobDrops != null) {
            onMobDrops.handle(new MCEntityLivingDeathDropsEvent(event));
        }
        super.onMobDrops(event);
    }

    @Override
    public void onAttackMob(LivingHurtEvent event) {
        if (onAttackMob != null) {
            onAttackMob.handle(new MCEntityLivingHurtEvent(event));
        }
        super.onAttackMob(event);
    }

    @Override
    public void onHurt(LivingHurtEvent event) {
        if (onHurt != null) {
            onHurt.handle(new MCEntityLivingHurtEvent(event));
        }
        super.onHurt(event);
    }

    @Override
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (onRightClickBlock != null) {
            onRightClickBlock.handle(new MCPlayerRightClickBlockEvent(event));
        }
        super.onRightClickBlock(event);
    }

    @Override
    public void onEnderTeleport(EnderTeleportEvent event) {
        if (onEnderTeleport != null) {
            onEnderTeleport.handle(new MCEnderTeleportEvent(event));
        }
        super.onEnderTeleport(event);
    }

    @Override
    public void onKillMob(LivingDeathEvent event) {
        if (onKillMob != null) {
            onKillMob.handle(new MCEntityLivingDeathEvent(event));
        }
        super.onKillMob(event);
    }

    @Override
    public String getName() {
        return name == null ? super.getName() : name.getText();
    }

    @Override
    public String getDescription() {
        return description == null ? super.getDescription() : description.getText();
    }
}
