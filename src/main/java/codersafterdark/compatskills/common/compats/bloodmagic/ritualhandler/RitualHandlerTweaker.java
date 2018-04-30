package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.MessageStorage;
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
    public static void addRitualLock(String failureMessage, String ritual, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new AddRitualLock(failureMessage, ritual, requirements));
    }

    private static class AddRitualLock implements IAction {
        String failureMessage;
        String ritual;
        Ritual trueRitual;
        String[] requirements;

        AddRitualLock(String failureMessage, String ritual, String... requirements) {
            this.failureMessage = failureMessage;
            this.ritual = ritual;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkRitual(ritual)) {
                this.trueRitual = RitualRegistry.getRegistry().get(ritual);
                if (trueRitual != null) {
                    RitualLockKey ritualKey = new RitualLockKey(trueRitual);
                    MessageStorage.setFailureMessage(ritualKey, failureMessage);
                    LevelLockHandler.addLockByKey(ritualKey, RequirementHolder.fromStringList(requirements));
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
}
