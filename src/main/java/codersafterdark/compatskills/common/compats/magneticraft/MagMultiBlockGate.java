package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;

public class MagMultiBlockGate implements MultiBlockGate {
    private final String multiBlockName;

    public MagMultiBlockGate(String multiBlockName) {
        this.multiBlockName = multiBlockName;
    }

    @Override
    public String getMultiBlockName() {
        return multiBlockName;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof MagMultiBlockGate && getMultiBlockName().equals(((MagMultiBlockGate) o).getMultiBlockName());
    }

    @Override
    public int hashCode() {
        return multiBlockName.hashCode();
    }
}