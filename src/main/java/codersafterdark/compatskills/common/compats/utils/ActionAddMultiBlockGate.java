package codersafterdark.compatskills.common.compats.utils;

import com.google.common.base.Strings;
import crafttweaker.CraftTweakerAPI;
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
        String[] requirements = new String[defaultRequirements.length];
        if (Strings.isNullOrEmpty(getMultiBlockName())) {
            CraftTweakerAPI.logError("MultiBlock Name cannot be Empty");
        } else if (Strings.isNullOrEmpty(getFailureMessage())) {
            CraftTweakerAPI.logError("Failure Message cannot be Empty");
        }
        for (int x = 0; x < defaultRequirements.length; x++) {
            String currentString = defaultRequirements[x];
            if (Strings.isNullOrEmpty(currentString)) {
                CraftTweakerAPI.logError("Found Null String in defaultRequirements");
            }
        }
        addToHandler(new MultiBlockGate(multiBlockName, failureMessage, defaultRequirements));
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
