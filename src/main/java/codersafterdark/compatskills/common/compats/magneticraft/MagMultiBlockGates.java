package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.multiblock.MultiBlockAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("magneticraft")
@ZenClass("mods.compatskills.MagMultiBlockGates")
@ZenRegister
public class MagMultiBlockGates {
    @ZenMethod
    public static void addGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        if (CheckMethods.checkValidMultiblockNameMag(multiBlockName) && CheckMethods.checkString(failureMessage) && CheckMethods.checkStringArray(defaultRequirements)) {
            CompatSkills.LATE_ADDITIONS.add(new MultiBlockAction(new MagMultiBlockGate(multiBlockName), failureMessage, defaultRequirements));
        }
    }
}