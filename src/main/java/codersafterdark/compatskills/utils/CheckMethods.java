package codersafterdark.compatskills.utils;

import WayofTime.bloodmagic.ritual.RitualRegistry;
import blusunrize.immersiveengineering.api.MultiblockHandler;
import codersafterdark.compatskills.common.compats.reskillable.customcontent.CrTSkill;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import com.cout970.magneticraft.api.MagneticraftApi;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.List;

public class CheckMethods {

    public static boolean checkIItemstack(IItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            CraftTweakerAPI.logError("Itemstack: " + stack + " was found to be either null or empty!");
            return false;
        }
        return true;
    }

    public static boolean checkStringArray(String[] strings) {
        if (strings == null || strings.length == 0) {
            CraftTweakerAPI.logError("String Array 'locked' was found to have no entries!");
            return false;
        } else {
            for (String string : strings) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError("String: " + string + " was found to be either null or empty!");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkString(String message) {
        if (message == null || message.isEmpty()) {
            CraftTweakerAPI.logError("'String' Param is either null or empty!");
            return false;
        }
        return true;
    }

    public static boolean checkInt(int i) {
        if (i < 0) {
            CraftTweakerAPI.logError("integer was found to be lower than 0, this is not allowed!");
            return false;
        }
        return true;
    }

    public static boolean checkModLoaded(String modid) {
        if (!Loader.isModLoaded(modid)) {
            CraftTweakerAPI.logError("Mod Id: " + modid + " Is not Loaded!");
            return false;
        }
        return true;
    }

    public static boolean checkValidNBTTagCompound(IData tag) {
        if (!(tag instanceof DataMap)) {
            if (tag != null) {
                CraftTweakerAPI.logError("Invalid NBT Tag: " + tag.asString());
            }
            return false;
        }
        return true;
    }

    /////////////////
    // Blood Magic //
    /////////////////
    public static boolean checkRitual(String ritual) {
        if (ritual == null || ritual.isEmpty()) {
            CraftTweakerAPI.logError("String Ritual was Null or Empty!");
            return false;
        } else if (!RitualRegistry.ritualEnabled(ritual)) {
            CraftTweakerAPI.logError("Invalid or Disabled Ritual String!");
        }
        return true;
    }

    /////////////////
    //   Traits    //
    /////////////////

    public static boolean checkIntX(int x) {
        if (x < 0 || x > 4) {
            CraftTweakerAPI.logError("X-Pos needs to be between 0 and 4");
            return false;
        }
        return true;
    }

    public static boolean checkIntY(int y) {
        if (y < 0 || y > 3) {
            CraftTweakerAPI.logError("Y-Pos needs to be between 0 and 3");
            return false;
        }
        return true;
    }

    public static boolean checkParentSkillsString(String parent) {
        if (parent == null || parent.isEmpty()) {
            CraftTweakerAPI.logError("String for Parent Skill was found to be null or empty!");
            return false;
        } else if (!ReskillableRegistries.SKILLS.containsKey(new ResourceLocation(parent))) {
            CraftTweakerAPI.logError("String Resource Location is not a Valid Skill!");
            return false;
        }
        return true;
    }

    public static boolean checkCrTSkillParent(CrTSkill parent) {
        if (parent == null) {
            CraftTweakerAPI.logError("CrTSkill Parent is found to be Null!");
            return false;
        }
        return checkParentSkillsString(parent.getRegistryName().toString());
    }

    public static boolean checkSkill(Skill skill) {
        if (skill == null) {
            CraftTweakerAPI.logError("Skill is found to be Null!");
            return false;
        }
        return checkParentSkillsString(skill.getRegistryName().toString());
    }

    ////////////////////////////////
    //   Immersive Engineering    //
    ////////////////////////////////

    public static boolean checkValidMultiblockNameIE(String multiBlock){
        List<MultiblockHandler.IMultiblock> multiblocks = MultiblockHandler.getMultiblocks();
        if (multiBlock == null || multiBlock.isEmpty()) {
            CraftTweakerAPI.logError("String for Multiblock Name was found to be null or empty!");
            return false;
        } else if (MultiblockHandler.getMultiblocks().parallelStream().map(MultiblockHandler.IMultiblock::getUniqueName).noneMatch(multiBlock::equals)) {
            CraftTweakerAPI.logError("No valid match was found for the String: " + multiBlock);
            return false;
        }
        return true;
    }

    ///////////////////////
    //   Magneticraft    //
    ///////////////////////

    public static boolean checkValidMultiblockNameMag(String multiBlock) {
        if (multiBlock == null || multiBlock.isEmpty()) {
            CraftTweakerAPI.logError("String for Multiblock Name was found to be null or empty!");
            return false;
        } else if (!MagneticraftApi.getMultiblockManager().getRegisteredMultiblocks().containsKey(multiBlock)) {
            CraftTweakerAPI.logError("No valid match was found for the String: " + multiBlock);
            return false;
        }
        return true;
    }
}
