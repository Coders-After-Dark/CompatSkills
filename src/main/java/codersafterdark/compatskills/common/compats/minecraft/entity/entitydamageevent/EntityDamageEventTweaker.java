package codersafterdark.compatskills.common.compats.minecraft.entity.entitydamageevent;

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

@ZenClass("mods.compatskills.EntityDamageLock")
@ZenRegister
public class EntityDamageEventTweaker {
    @ZenMethod
    public static void addEntityLock(IEntityDefinition definition, String... defaultRequirements) {
        if (MinecraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddDamageLock(definition, defaultRequirements));
        }
    }

    private static class AddDamageLock implements IAction {
        private final IEntityDefinition definition;
        private final String[] requirements;

        private AddDamageLock(IEntityDefinition definition, String... requirements) {
            this.definition = definition;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidIEntityDefinition(definition) & CheckMethods.checkStringArray(requirements)) {
                MinecraftCompatHandler.addMCLock(new EntityDamageKey(definition.getId()), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Entity Damage Lock for Entity: " + (definition == null ? "null" : definition.getName()) + ", With Requirements: " + descString;
        }
    }
}