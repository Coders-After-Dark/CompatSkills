package codersafterdark.compatskills.common.compats.minecraft.entity.animaltameevent;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.MinecraftCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ZenClass("mods.compatskills.AnimalTameLock")
@ZenRegister
public class AnimalTameEventTweaker {
    @ZenMethod
    public static void addTameLock(IEntityDefinition definition, String... defaultRequirements) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddTameLock(definition, defaultRequirements));
        }
    }

    private static class AddTameLock implements IAction {
        private final IEntityDefinition definition;
        private final String[] requirements;

        private AddTameLock(IEntityDefinition definition, String... requirements) {
            this.definition = definition;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidIEntityDefinition(definition) & CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new EntityTameKey(definition.getId()), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Entity Tame Lock for Entity: " + (definition == null ? "null" : definition.getName()) + ", With Requirements: " + descString;
        }
    }
}