package codersafterdark.compatskills.common.compats.immersiveengineering;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.multiblock.MultiBlockAction;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.RequirementHolder;
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
        CompatSkills.LATE_ADDITIONS.add(new IEMultiBlockAction(multiBlockName, failureMessage, defaultRequirements));
    }

    private static class IEMultiBlockAction extends MultiBlockAction {
        private IEMultiBlockAction(String gateName, String failureMessage, String... defaultRequirements) {
            super(gateName, failureMessage, defaultRequirements);
        }

        @Override
        protected MultiBlockGate getGate() {
            return CheckMethods.checkValidMultiblockNameIE(multiblockName) ? new IEMultiBlockGate(multiblockName) : null;
        }

        @Override
        protected void addLock(MultiBlockGate gate, RequirementHolder holder) {
            IECompatHandler.addIELock(gate, holder);
        }
    }
}