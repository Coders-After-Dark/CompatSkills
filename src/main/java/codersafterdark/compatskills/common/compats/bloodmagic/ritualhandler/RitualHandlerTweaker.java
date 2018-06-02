package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCostLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCrystalLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualNameLockKey;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("bloodmagic")
@ZenClass("mods.compatskills.RitualHandler")
@ZenRegister
public class RitualHandlerTweaker {
    @ZenMethod
    public static void addRitualLock(String ritual, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddRitualLock(ritual, requirements));
    }

    @ZenMethod
    public static void addRitualCostLock(int activationCost, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddRitualCostLock(activationCost, requirements));
    }

    @ZenMethod
    public static void addRitualCrystalLock(int crystalLevel, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddRitualCrystalLock(crystalLevel, requirements));
    }

    private static class AddRitualLock implements IAction {
        private final String ritual;
        private final String[] requirements;

        private AddRitualLock(String ritual, String... requirements) {
            this.ritual = ritual;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkRitual(ritual) & CheckMethods.checkStringArray(requirements)) {
                Ritual trueRitual = RitualRegistry.getRegistry().get(ritual);
                if (trueRitual != null) {
                    LevelLockHandler.addLockByKey(new RitualNameLockKey(trueRitual), RequirementHolder.fromStringList(requirements));
                }
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Ritual Lock for Ritual: " + ritual + " With " + descString;
        }
    }

    private static class AddRitualCostLock implements IAction {
        private final int activationCost;
        private final String[] requirements;

        private AddRitualCostLock(int activationCost, String... requirements) {
            this.activationCost = activationCost;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkInt(activationCost) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new RitualCostLockKey(activationCost), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Ritual Lock for rituals with a costs equal to " + activationCost + " With " + descString;
        }
    }

    private static class AddRitualCrystalLock implements IAction {
        private final int crystalLevel;
        private final String[] requirements;

        private AddRitualCrystalLock(int crystalLevel, String... requirements) {
            this.crystalLevel = crystalLevel;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkInt(crystalLevel) & CheckMethods.checkStringArray(requirements)) {
                LevelLockHandler.addLockByKey(new RitualCrystalLockKey(crystalLevel), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            StringBuilder descString = new StringBuilder("Requirements: ");
            for (String string : requirements) {
                descString.append(string).append(", ");
            }
            return "Added Ritual Lock for rituals with a crystal requirement of level: " + crystalLevel + " With " + descString;
        }
    }
}