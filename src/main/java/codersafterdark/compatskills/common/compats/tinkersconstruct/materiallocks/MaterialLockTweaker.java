package codersafterdark.compatskills.common.compats.tinkersconstruct.materiallocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("tconstruct")
@ZenClass("mods.compatskills.MaterialLock")
@ZenRegister
public class MaterialLockTweaker {

    @ZenMethod
    public static void addMaterialLock(String id, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddMaterialLock(id, requirements));
    }

    private static class AddMaterialLock implements IAction {
        String id;
        String[] requirements;

        AddMaterialLock(String id, String... requirements) {
            this.id = id;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkMaterial(id)) {
                Material material = TinkerRegistry.getMaterial(id);
                LevelLockHandler.addLockByKey(new MaterialLockKey(material), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Material Lock for Material: " + id + " With " + descString;
        }
    }
}
