package codersafterdark.compatskills.common.compats.thaumcraft.keys;

import codersafterdark.reskillable.api.data.LockKey;
import thaumcraft.api.research.ResearchCategory;

import java.util.Objects;

public class KnowledgeKey implements LockKey {
    private final ResearchCategory category;
    private final String knowledgeType;

    public KnowledgeKey(ResearchCategory category, String knowledgeType) {
        this.category = category;
        this.knowledgeType = knowledgeType == null ? "" : knowledgeType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof KnowledgeKey) {
            KnowledgeKey other = (KnowledgeKey) o;
            if (category == null) {
                return other.category == null && knowledgeType.equals(other.knowledgeType);
            }
            return category.equals(other.category) && knowledgeType.equals(other.knowledgeType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, knowledgeType);
    }
}