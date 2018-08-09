package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.thaumcraft.keys.KnowledgeKey;
import codersafterdark.compatskills.common.compats.thaumcraft.keys.ResearchKey;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;

@ModOnly("thaumcraft")
@ZenClass("mods.compatskills.Thaumcraft")
@ZenRegister
public class ThaumcraftTweaker {
    @ZenMethod
    public static void addResearchLock(String researchKey, String... requirements) {
        if (ThaumcraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddResearchLock(researchKey, requirements));
        }
    }

    @ZenMethod
    public static void addKnowledgeLock(String categoryName, String knowledgeType, String... requirements) {
        if (ThaumcraftCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddKnowledgeLock(categoryName, knowledgeType, requirements));
        }
    }

    private static class AddResearchLock implements IAction {
        private final String researchKey;
        private final String[] requirements;

        private AddResearchLock(String researchKey, String... requirements) {
            this.researchKey = researchKey;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkString(researchKey) & CheckMethods.checkStringArray(requirements)) {
                ThaumcraftCompatHandler.addThaumcraftLock(new ResearchKey(researchKey), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Research Lock for: " + researchKey + " With Requirements: " + Utils.formatRequirements(requirements);
        }
    }

    private static class AddKnowledgeLock implements IAction {
        private final String categoryName, knowledgeType;
        private final String[] requirements;

        private AddKnowledgeLock(String categoryName, String knowledgeType, String... requirements) {
            this.categoryName = categoryName;
            this.knowledgeType = knowledgeType;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkCategory(categoryName) & CheckMethods.checkKnowledgeType(knowledgeType) & CheckMethods.checkStringArray(requirements)) {
                ResearchCategory category = ResearchCategories.getResearchCategory(categoryName);
                if (category != null) {
                    ThaumcraftCompatHandler.addThaumcraftLock(new KnowledgeKey(category, knowledgeType), RequirementHolder.fromStringList(requirements));
                }
            }
        }

        @Override
        public String describe() {
            return "Added Knowledge Lock for category: + " + categoryName + " of knowledge type: " + knowledgeType + " With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}