package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.BloodMagic;
import WayofTime.bloodmagic.ritual.Ritual;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.bloodmagic.BMCompatHandler;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCostLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualCrystalLockKey;
import codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler.keys.RitualNameLockKey;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ModOnly("bloodmagic")
@ZenClass("mods.compatskills.RitualHandler")
@ZenRegister
public class RitualHandlerTweaker {
    @ZenMethod
    public static void addRitualLock(String ritual, String... requirements) {
        if (BMCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddRitualLock(ritual, requirements));
        }
    }

    @ZenMethod
    public static void addRitualCostLock(int activationCost, String... requirements) {
        if (BMCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddRitualCostLock(activationCost, requirements));
        }
    }

    @ZenMethod
    public static void addRitualCrystalLock(int crystalLevel, String... requirements) {
        if (BMCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddRitualCrystalLock(crystalLevel, requirements));
        }
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
                Ritual trueRitual = BloodMagic.RITUAL_MANAGER.getRitual(ritual);
                if (trueRitual != null) {
                    BMCompatHandler.addBMLock(new RitualNameLockKey(trueRitual), RequirementHolder.fromStringList(requirements));
                }
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Ritual Lock for Ritual: " + ritual + " With Requirements: " + descString;
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
                BMCompatHandler.addBMLock(new RitualCostLockKey(activationCost), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Ritual Lock for rituals with a costs equal to " + activationCost + " With Requirements: " + descString;
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
                BMCompatHandler.addBMLock(new RitualCrystalLockKey(crystalLevel), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(requirements).map(string -> string + ", ").collect(Collectors.joining());
            return "Added Ritual Lock for rituals with a crystal requirement of level: " + crystalLevel + " With Requirements: " + descString;
        }
    }
}