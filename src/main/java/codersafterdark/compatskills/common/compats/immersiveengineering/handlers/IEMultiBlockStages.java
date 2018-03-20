package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("immersiveengineering")
@ZenClass("mods.compatskills.IEMultiBlockGate")
@ZenRegister
public class IEMultiBlockStages {
    @ZenMethod
    public static void addGate(String multiBlockName, String failureMessage, String... defaultRequirements){
        CraftTweakerAPI.apply(new ActionAddIEMultiBlockGate(multiBlockName, failureMessage, defaultRequirements));
    }
}
