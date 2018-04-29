package codersafterdark.compatskills.utils.multiblock;

import codersafterdark.compatskills.common.compats.gamestages.GameStageLocks.GameStageLock;
import codersafterdark.reskillable.api.data.LockKey;

public class MultiBlockGate implements LockKey {//TODO add an IE and Mag version?
    private final String multiBlockName;

    public MultiBlockGate(String multiBlockName) {
        this.multiBlockName = multiBlockName;
    }

    public String getMultiBlockName() {
        return multiBlockName;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof MultiBlockGate && multiBlockName.equals(((MultiBlockGate) o).multiBlockName);
    }

    @Override
    public int hashCode() {
        return multiBlockName.hashCode();
    }
}
