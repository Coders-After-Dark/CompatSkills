package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import codersafterdark.compatskills.common.compats.immersiveengineering.IECompatHandler;
import codersafterdark.compatskills.common.compats.utils.ActionAddMultiBlockGate;
import codersafterdark.compatskills.common.compats.utils.MultiBlockGate;

public class ActionAddIEMultiBlockGate extends ActionAddMultiBlockGate {

    public ActionAddIEMultiBlockGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        super(multiBlockName, failureMessage, defaultRequirements);
    }

    @Override
    public void addToHandler(MultiBlockGate multiBlockGate) {
        IECompatHandler.addMultiBlockGate(multiBlockGate);
    }
}
