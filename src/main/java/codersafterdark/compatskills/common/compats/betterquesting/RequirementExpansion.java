package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.api.IQuestExpansion;
import betterquesting.api.api.QuestExpansion;

@QuestExpansion
public class RequirementExpansion implements IQuestExpansion {
    @Override
    public void loadExpansion() {
        BetterQuestingHandler.registerExpansion();
    }
}