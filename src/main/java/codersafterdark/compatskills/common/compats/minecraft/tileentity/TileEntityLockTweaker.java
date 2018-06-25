package codersafterdark.compatskills.common.compats.minecraft.tileentity;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ZenClass("mods.compatskills.TileEntityLock")
@ZenRegister
public class TileEntityLockTweaker {
    @ZenMethod
    public static void addTileEntityLock(String tileName, String... locked) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddTileEntity(tileName, locked));
        }
    }

    private static class AddTileEntity implements IAction {
        private final String tileName;
        private final String[] requirements;

        private AddTileEntity(String tileName, String... requirements) {
            this.tileName = tileName;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidTileEntityName(tileName) & CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new TileEntityLockKey(tileName), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Setting the requirement of Tile Entity: " + tileName + " to Requirements: " + descString;
        }
    }
}