package codersafterdark.compatskills.utils.multiblock;

import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;

public class MultiBlockAction implements IAction {
    private final MultiBlockGate gate;
    private final String failureMessage;
    private final String[] defaultRequirements;

    public MultiBlockAction(MultiBlockGate gate, String failureMessage, String... defaultRequirements) {
        this.gate = gate;
        this.failureMessage = failureMessage;
        this.defaultRequirements = defaultRequirements;
    }

    @Override
    public void apply() {
        if (gate != null){
            LevelLockHandler.addLockByKey(gate, RequirementHolder.fromStringList(defaultRequirements));
            MessageStorage.setFailureMessage(gate, failureMessage);
        }
    }

    @Override
    public String describe() {
        StringBuilder descString = new StringBuilder("Requirements: ");
        for (String string : defaultRequirements) {
            descString.append(string).append(", ");
        }
        return "Added MultiBlock " + gate.getMultiBlockName() + " With " + descString;
    }
}