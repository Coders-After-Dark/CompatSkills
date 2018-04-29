package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.GenericNBTLockKey;
import codersafterdark.reskillable.api.data.ModLockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.NBTLock")
@ZenRegister
public class NBTLockTweaker {

    //TODO add error messages if the tags are not instances of DataMap

    @ZenMethod
    public static void addModNBTLock(String modId, IData tag, String... locked) {
        StringBuilder descString = new StringBuilder("Requirements: ");

        if (CheckMethods.checkStringArray(locked)) {
            for (String string : locked) {
                descString.append(string).append(", ");
            }
        }

        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                if (CheckMethods.checkString(modId) & CheckMethods.checkModLoaded(modId) & tag instanceof DataMap & CheckMethods.checkStringArray(locked)) {
                    RequirementHolder holder = RequirementHolder.fromStringList(locked);
                    LevelLockHandler.addLockByKey(new ModLockKey(modId, (NBTTagCompound) NBTConverter.from(tag)), holder);
                }
            }

            @Override
            public String describe() {
                return "Adding NBT lock: " + (!(tag instanceof DataMap) ? "invalid" : tag.asString()) + " for Mod: " + modId + " to: " + descString;
            }
        });
    }

    @ZenMethod
    public static void addGenericNBTLock(IData tag, String... locked) {
        StringBuilder descString = new StringBuilder("Requirements: ");

        if (CheckMethods.checkStringArray(locked)) {
            for (String string : locked) {
                descString.append(string).append(", ");
            }
        }

        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                if (tag instanceof DataMap & CheckMethods.checkStringArray(locked)) {
                    RequirementHolder holder = RequirementHolder.fromStringList(locked);
                    LevelLockHandler.addLockByKey(new GenericNBTLockKey((NBTTagCompound) NBTConverter.from(tag)), holder);
                }
            }

            @Override
            public String describe() {
                return "Adding Generic NBT lock: " + (!(tag instanceof DataMap) ? "invalid" : tag.asString()) + " to: " + descString;
            }
        });
    }
}