package codersafterdark.compatskills.common.compats.minecraft.entity.entitymountevent;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.minecraft.entity.EntityLockKey;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.EntityMountLock")
@ZenRegister
public class EntityMountEventTweaker {
    @ZenMethod
    public static void addMountLock(IEntity entity, String... defaultRequirements){
        CompatSkills.LATE_ADDITIONS.add(new AddMountLock(entity, defaultRequirements));
    }

    private static class AddMountLock implements IAction {
        private final IEntity entity;
        private final String[] requirements;

        AddMountLock(IEntity entity, String... requirements){
            this.entity = entity;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidIEntity(entity) & CheckMethods.checkStringArray(requirements)){
                LevelLockHandler.addLockByKey(new EntityLockKey(CraftTweakerMC.getEntity(entity)), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Entity Mount Lock for Entity: " + entity.getDisplayName() + ", With Requirements: " + descString;
        }
    }
}
