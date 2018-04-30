package codersafterdark.compatskills.common.compats.immersiveengineering;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.multiblock.MultiBlockAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("immersiveengineering")
@ZenClass("mods.compatskills.IEMultiBlockGate")
@ZenRegister
public class IEMultiBlockGates {
    @ZenMethod
    public static void addGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        if (CheckMethods.checkString(failureMessage) && CheckMethods.checkStringArray(defaultRequirements)) {
            CompatSkills.LATE_ADDITIONS.add(new MultiBlockAction(new IEMultiBlockGate(multiBlockName), failureMessage, defaultRequirements));
        }
    }
}
