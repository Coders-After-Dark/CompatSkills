package codersafterdark.compatskills.common.compats.thaumcraft.keys;

import codersafterdark.reskillable.api.data.LockKey;

public class ResearchKey implements LockKey {
    private final String researchKey;

    public ResearchKey(String researchKey) {
        this.researchKey = researchKey;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ResearchKey && researchKey.equals(((ResearchKey) obj).researchKey);
    }

    @Override
    public int hashCode() {
        return researchKey.hashCode();
    }
}
