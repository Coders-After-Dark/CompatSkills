package codersafterdark.compatskills.common.compats.minecraft.tileentity;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.TileEntityLock")
@ZenRegister
public class TileEntityLockTweaker {
    @ZenMethod
    public static void addTileEntityLock(String tileName, String... locked) {
        CompatSkills.LATE_ADDITIONS.add(new AddTileEntity(tileName, locked));
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
                LevelLockHandler.addLockByKey(new TileEntityLockKey(tileName), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }

            return "Setting the requirement of Tile Entity: " + tileName + " to " + descString;
        }
    }
}