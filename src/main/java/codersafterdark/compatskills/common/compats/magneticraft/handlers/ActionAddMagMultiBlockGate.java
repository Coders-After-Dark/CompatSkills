package codersafterdark.compatskills.common.compats.magneticraft.handlers;

import codersafterdark.compatskills.common.compats.magneticraft.MagCompatHandler;
import codersafterdark.compatskills.utils.multiblock.ActionAddMultiBlockGate;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;

public class ActionAddMagMultiBlockGate extends ActionAddMultiBlockGate {

    public ActionAddMagMultiBlockGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        super(multiBlockName, failureMessage, defaultRequirements);
    }

    @Override
    public void addToHandler(MultiBlockGate multiBlockGate) {
        MagCompatHandler.addMultiBlockGate(multiBlockGate);
    }
}
