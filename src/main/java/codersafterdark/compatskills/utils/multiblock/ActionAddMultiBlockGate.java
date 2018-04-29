package codersafterdark.compatskills.utils.multiblock;

import codersafterdark.compatskills.utils.CheckMethods;
import crafttweaker.IAction;

import java.util.Arrays;

public abstract class ActionAddMultiBlockGate implements IAction {
    private final String multiBlockName;
    private final String[] defaultRequirements;
    private final String failureMessage;

    public ActionAddMultiBlockGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        this.multiBlockName = multiBlockName;
        this.failureMessage = failureMessage;
        this.defaultRequirements = defaultRequirements;
    }

    @Override
    public void apply() {
        if (CheckMethods.checkString(multiBlockName) && CheckMethods.checkStringArray(defaultRequirements) && CheckMethods.checkString(failureMessage)) {
            addToHandler(new MultiBlockGate(multiBlockName, failureMessage, defaultRequirements));
        }
    }

    public abstract void addToHandler(MultiBlockGate multiBlockGate);

    @Override
    public String describe() {
        return "Added MultiBlock " + this.getMultiBlockName() + " With Requirements: " + Arrays.toString(defaultRequirements);
    }

    public String getMultiBlockName() {
        return multiBlockName;
    }

    public String getFailureMessage() {
        return failureMessage;
    }
}
