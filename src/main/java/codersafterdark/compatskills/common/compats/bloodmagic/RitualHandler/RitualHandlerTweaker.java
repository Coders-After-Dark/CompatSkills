package codersafterdark.compatskills.common.compats.bloodmagic.RitualHandler;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.bloodmagic.BMCompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ModOnly("bloodmagic")
@ZenClass("mods.compatskills.RitualHandler")
@ZenRegister
public class RitualHandlerTweaker {

    @ZenMethod
    public static void addRitualLock(String failureMessage, String ritual, String... requirements) {
        CompatSkills.LATE_ADDITIONS.add(new Add(failureMessage, ritual, requirements));
    }

    private static class Add implements IAction {
        String failureMessage;
        String ritualString;
        RequirementHolder holder;
        String[] requirements;

        Add(String failureMessage, String ritual, String... requirements) {
            if (CheckMethods.checkString(failureMessage)){
                this.failureMessage = failureMessage;
            }

            this.ritualString = ritual;

            if (CheckMethods.checkStringArray(requirements)){
                this.holder = RequirementHolder.fromStringList(requirements);
                this.requirements = requirements;
            }
        }

        @Override
        public void apply() {
            BMCompatHandler.addRitualLock(failureMessage, RitualRegistry.getRegistry().get(ritualString), holder);
        }

        @Override
        public String describe() {
            StringBuilder reqString = new StringBuilder("Requirements: ");
            for (String string : requirements){
                reqString.append(string).append(", ");
            }
            return "Adding Ritual Lock for Ritual: " + ritualString + " With Requirements: " + reqString;
        }
    }
}
