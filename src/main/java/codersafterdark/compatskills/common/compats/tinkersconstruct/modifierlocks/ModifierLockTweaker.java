package codersafterdark.compatskills.common.compats.tinkersconstruct.modifierlocks;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("tconstruct")
@ZenClass("mods.compatskills.ModifierLock")
@ZenRegister
public class ModifierLockTweaker {

    @ZenMethod
    public static void addModifierLock(String identifier, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddModifierLock(identifier, requirements));
    }

    private static class AddModifierLock implements IAction {
        String id;
        String[] requirements;

        AddModifierLock(String id, String... requirements) {
            this.id = id;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkModifier(id)) {
                IModifier modifier = TinkerRegistry.getModifier(id);
                if (modifier != null) {
                    LevelLockHandler.addLockByKey(new ModifierLockKey(modifier), RequirementHolder.fromStringList(requirements));
                }
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Modifier Lock for Ritual: " + id + " With " + descString;
        }
    }
}
