package codersafterdark.compatskills.common.compats.thaumcraft.keys;

import codersafterdark.reskillable.api.data.LockKey;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.research.ResearchCategory;

public class KnowledgeKey implements LockKey {
    private final String categoryName;
    private final String knowledgeEnumType;

    public KnowledgeKey(String categoryName, String knowledgeEnumType) {
        this.categoryName = categoryName;
        this.knowledgeEnumType = knowledgeEnumType;
    }

    public KnowledgeKey(EnumKnowledgeType knowledgeType, ResearchCategory category) {
        this.categoryName = category.key;
        this.knowledgeEnumType = knowledgeType.getAbbreviation();
    }
}
