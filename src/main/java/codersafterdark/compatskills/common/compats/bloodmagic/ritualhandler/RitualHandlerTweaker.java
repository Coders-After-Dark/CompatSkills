package codersafterdark.compatskills.common.compats.bloodmagic.ritualhandler;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
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
        if (CheckMethods.checkString(failureMessage) && CheckMethods.checkRitual(ritual) && CheckMethods.checkStringArray(requirements)) {
            Ritual trueRitual = RitualRegistry.getRegistry().get(ritual);
            if (trueRitual != null) {
                RitualLockKey ritualKey = new RitualLockKey(trueRitual);
                MessageStorage.setFailureMessage(ritualKey, failureMessage);
                LevelLockHandler.addLockByKey(ritualKey, RequirementHolder.fromStringList(requirements));
            }
        }
    }
}
