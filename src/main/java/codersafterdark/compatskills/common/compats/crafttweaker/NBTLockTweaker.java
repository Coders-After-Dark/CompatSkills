package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.GenericNBTLockKey;
import codersafterdark.reskillable.api.data.ModLockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.NBTLock")
@ZenRegister
public class NBTLockTweaker {
    @ZenMethod
    public static void addModNBTLock(String modId, IData tag, String... locked) {
        CompatSkills.LATE_ADDITIONS.add(new AddModNBTLock(modId, tag, locked));
    }

    @ZenMethod
    public static void addGenericNBTLock(IData tag, String... locked) {
        CompatSkills.LATE_ADDITIONS.add(new AddGenericNBTLock(tag, locked));
    }

    private static class AddModNBTLock implements IAction {
        String modID;
        IData data;
        String[] requirements;

        AddModNBTLock(String modId, IData tag, String... locked) {
            if (CheckMethods.checkString(modId) && CheckMethods.checkModLoaded(modId) && CheckMethods.checkValidNBTTagCompound(tag) && CheckMethods.checkStringArray(locked)) {
                this.modID = modId;
                this.data = tag;
                this.requirements = locked;
            }
        }

        @Override
        public void apply() {
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            LevelLockHandler.addLockByKey(new ModLockKey(modID, (NBTTagCompound) NBTConverter.from(data)), holder);
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Adding NBT lock: " + data.asString() + " for Mod: " + modID + " to: " + descString;
        }
    }

    private static class AddGenericNBTLock implements IAction {
        IData data;
        String[] requirements;

        AddGenericNBTLock(IData data, String... requirements) {
            if (CheckMethods.checkValidNBTTagCompound(data) && CheckMethods.checkStringArray(requirements)) {
                this.data = data;
                this.requirements = requirements;
            }
        }

        @Override
        public void apply() {
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            LevelLockHandler.addLockByKey(new GenericNBTLockKey((NBTTagCompound) NBTConverter.from(data)), holder);
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Adding Generic NBT lock: " + data.asString() + " to: " + descString;
        }
    }
}