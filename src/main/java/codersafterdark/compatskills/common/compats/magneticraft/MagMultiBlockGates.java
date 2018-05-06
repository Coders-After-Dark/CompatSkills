package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.multiblock.MultiBlockAction;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
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
        CompatSkills.LATE_ADDITIONS.add(new MagMultiBlockAction(multiBlockName, failureMessage, defaultRequirements));
    }

    private static class MagMultiBlockAction extends MultiBlockAction {
        private MagMultiBlockAction(String gateName, String failureMessage, String... defaultRequirements) {
            super(gateName, failureMessage, defaultRequirements);
        }

        @Override
        protected MultiBlockGate getGate() {
            return CheckMethods.checkValidMultiblockNameMag(multiblockName) ? new MagMultiBlockGate(multiblockName) : null;
        }
    }
}