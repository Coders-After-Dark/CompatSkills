package codersafterdark.compatskills.utils;

import codersafterdark.reskillable.api.data.RequirementHolder;

public class MultiBlockGate {
    private final String multiBlockName;
    private final String failureMessage;
    private final RequirementHolder requirementHolder;

    public MultiBlockGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        this.multiBlockName = multiBlockName;
        this.failureMessage = failureMessage;
        this.requirementHolder = RequirementHolder.fromStringList(defaultRequirements);
    }

    public String getMultiBlockName() {
        return multiBlockName;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public RequirementHolder getRequirementHolder() {
        return requirementHolder;
    }
}
