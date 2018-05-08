package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.EntityMountLock")
@ZenRegister
public class EntityMountEventTweaker {
    @ZenMethod
    public static void addMountLock(IEntityDefinition definition, String... defaultRequirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddMountLock(definition, defaultRequirements));
    }

    private static class AddMountLock implements IAction {
        private final IEntityDefinition definition;
        private final String[] requirements;

        private AddMountLock(IEntityDefinition definition, String... requirements) {
            this.definition = definition;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidIEntityDefinition(definition) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new EntityMountKey(definition.getId()), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Entity Mount Lock for Entity: " + (definition == null ? "null" : definition.getName()) + ", With " + descString;
        }
    }
}
