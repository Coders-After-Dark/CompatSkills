package codersafterdark.compatskills.utils;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.RequirementHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String formatRequirements(String[] requirements) {
        if (requirements == null) {
            return "null";
        }
        String reqString = Arrays.stream(requirements).map(s -> s + ", ").collect(Collectors.joining());
        if (!reqString.isEmpty()) {
            reqString = reqString.substring(0, reqString.length() - 2);
        }
        return reqString;
    }

    public static ITextComponent getError(RequirementHolder holder, PlayerData data, TextComponentTranslation error) {
        TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.requirements");
        String reqString = holder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
        return new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
    }

    public static RayTraceResult getLookingAt(EntityPlayer player) {
        if (player == null) {
            return null;
        }
        //Slightly reorganized Item.rayTrace
        Vec3d eyes = new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
        float pitch = -player.rotationPitch * 0.017453292F;
        float yaw = -player.rotationYaw * 0.017453292F - (float) Math.PI;
        double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        double pitchReach = -MathHelper.cos(pitch) * reach;
        Vec3d vec3d = eyes.add((double) MathHelper.sin(yaw) * pitchReach, (double) MathHelper.sin(pitch) * reach, (double) MathHelper.cos(yaw) * pitchReach);
        return player.getEntityWorld().rayTraceBlocks(eyes, vec3d);
    }

    //Based on EntityRenderer.getMouseOver
    public static Entity lookingAt(EntityPlayer player) {
        if (player == null) {
            return null;
        }
        double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

        double distance = reach;

        Vec3d eyes = new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
        RayTraceResult objectMouseOver = getLookingAt(player);
        if (objectMouseOver != null) {
            distance = objectMouseOver.hitVec.distanceTo(eyes);
        }

        Vec3d look = player.getLook(1.0F);
        double lookReachX = look.x * reach;
        double lookReachY = look.y * reach;
        double lookReachZ = look.z * reach;

        List<Entity> list = player.getEntityWorld().getEntitiesInAABBexcluding(player,
                player.getEntityBoundingBox().expand(lookReachX, lookReachY, lookReachZ).grow(1.0D, 1.0D, 1.0D),
                ent -> ent != null && (!(ent instanceof EntityPlayer) || !((EntityPlayer) ent).isSpectator()) && ent.canBeCollidedWith());
        double bestDistance = distance;

        Vec3d direction = eyes.add(lookReachX, lookReachY, lookReachZ);
        Entity hitEntity = null;

        for (Entity entity : list) {
            AxisAlignedBB entityBB = entity.getEntityBoundingBox().grow((double) entity.getCollisionBorderSize());
            if (entityBB.contains(eyes)) {
                //The entity's bounding box has the eyes in it
                return entity;
            }
            RayTraceResult raytraceresult = entityBB.calculateIntercept(eyes, direction);
            if (raytraceresult != null) {
                double dist = eyes.distanceTo(raytraceresult.hitVec);
                if (dist == 0.0D) {
                    return entity;
                }
                if (dist < bestDistance) {
                    hitEntity = entity;
                    bestDistance = dist;
                }
            }
        }
        return hitEntity;
    }

    //returns no_registry_name like CraftTweaker does so that it can be marked as equal
    public static String getEntityID(Entity entity) {
        for (EntityEntry entry : ForgeRegistries.ENTITIES.getValuesCollection()) {
            if (entry.getEntityClass() == entity.getClass()) {
                return entry.getRegistryName() != null ? entry.getRegistryName().toString() : "no_registry_name";
            }
        }
        return "no_registry_name";
    }
}