package codersafterdark.compatskills.common.compats.bloodmagic.RitualHandler;

import WayofTime.bloodmagic.core.registry.RitualRegistry;
import WayofTime.bloodmagic.ritual.data.Ritual;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.compatskills.RitualHandler")
@ZenRegister
public class RitualHandlerTweaker {

    @ZenMethod
    public static void addRitualLock(String failureMessage, String ritual, String... requirements){
        RitualHandler handler = new RitualHandler();

        if (checkMessage(failureMessage) && checkRitual(ritual) && checkRequirements(requirements)){
            handler.setFailureMessage(failureMessage);
            RequirementHolder holder = RequirementHolder.fromStringList(requirements);
            Ritual ritual1 = RitualRegistry.getRegistry().get(ritual);
            handler.addRitualHolder(ritual1, holder);
        }
    }

    private static boolean checkMessage(String message){
        if (message == null || message.isEmpty()){
            CraftTweakerAPI.logError("'Failure Message' Param is either null or empty!");
            return false;
        }
        return true;
    }


    private static boolean checkRitual(String ritual){
        if (ritual == null || ritual.isEmpty()){
            CraftTweakerAPI.logError("String Ritual was Null or Empty!");
            return false;
        } else if (!RitualRegistry.getRegistry().containsKey(ritual)){
            CraftTweakerAPI.logError("Invalid Ritual String!");
        }
        return true;
    }


    private static boolean checkRequirements (String[] reqs){
        if (reqs == null || reqs.length == 0){
            CraftTweakerAPI.logError("No Requirements Specified");
            return false;
        }
        for (String req : reqs){
            if (req == null || req.isEmpty()){
                CraftTweakerAPI.logError("Requirement String was found to either be Null or Empty!");
                return false;
            }
        }
        return true;
    }
}
