package codersafterdark.compatskills.utils.multiblock;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.IAction;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class MultiBlockAction implements IAction {
    protected final String multiblockName;
    private final String failureMessage;
    private final String[] defaultRequirements;

    public MultiBlockAction(String multiblockName, String failureMessage, String... defaultRequirements) {
        this.multiblockName = multiblockName;
        this.failureMessage = failureMessage;
        this.defaultRequirements = defaultRequirements;
    }

    protected abstract MultiBlockGate getGate();

    @Override
    public void apply() {
        if (CheckMethods.checkString(failureMessage) & CheckMethods.checkStringArray(defaultRequirements)) {
            MultiBlockGate gate = getGate();
            if (gate != null) {
                LevelLockHandler.addLockByKey(gate, RequirementHolder.fromStringList(defaultRequirements));
                MessageStorage.setFailureMessage(gate, failureMessage);
            }
        }
    }

    @Override
    public String describe() {
        String descString = Arrays.stream(defaultRequirements).map(string -> string + ", ").collect(Collectors.joining());
        return "Added MultiBlock " + multiblockName + " With Requirements: " + descString;
    }
}