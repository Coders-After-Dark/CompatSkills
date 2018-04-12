package codersafterdark.compatskills.common.compats.bloodmagic.RitualHandler;

import WayofTime.bloodmagic.core.registry.RitualRegistry;
import WayofTime.bloodmagic.ritual.data.Ritual;
import codersafterdark.compatskills.common.compats.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.RitualHandler")
@ZenRegister
public class RitualHandlerTweaker {

    @ZenMethod
    public static void addRitualLock(String failureMessage, String ritual, String... requirements) {
        RitualHandler handler = new RitualHandler();
        if (CheckMethods.checkString(failureMessage) & CheckMethods.checkRitual(ritual) & CheckMethods.checkStringArray(requirements)) {
            handler.setFailureMessage(failureMessage);
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            Ritual trueRitual = RitualRegistry.getRegistry().get(ritual);
            handler.addRitualHolder(trueRitual, holder);
        }
    }
}
