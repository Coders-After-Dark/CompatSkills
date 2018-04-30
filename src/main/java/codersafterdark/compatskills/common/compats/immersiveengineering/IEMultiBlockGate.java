package codersafterdark.compatskills.common.compats.immersiveengineering;

import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;

public class IEMultiBlockGate implements MultiBlockGate {
    private String multiBlockName;

    public IEMultiBlockGate(String multiBlockName) {
        this.multiBlockName = multiBlockName;
    }

    @Override
    public String getMultiBlockName() {
        return multiBlockName;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof IEMultiBlockGate && getMultiBlockName().equals(((IEMultiBlockGate) o).getMultiBlockName());
    }

    @Override
    public int hashCode() {
        return multiBlockName.hashCode();
    }
}