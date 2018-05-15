package codersafterdark.compatskills.common.compats.reskillable.customcontent;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.compatskills.SkillCreator")
@ZenRegister
public class CrTSkill extends Skill {

    @ZenProperty
    public IFormattedText name;

    public CrTSkill(ResourceLocation name, ResourceLocation background) {
        super(name, background);
        ReskillableRegistries.SKILLS.register(this);
    }

    private static CrTSkill createSkill(ResourceLocation name, ResourceLocation background) {
        CrTSkill customSkill = new CrTSkill(name, background);
        if (ReskillableRegistries.SKILLS.containsKey(name)) {
            Skill value = ReskillableRegistries.SKILLS.getValue(name);
            if (value instanceof CrTSkill) {
                customSkill = (CrTSkill) value;
                if (!background.equals(customSkill.getBackground())) {
                    customSkill.background = background;
                    CraftTweakerAPI.logInfo("Loaded Skill: " + name + " - Updated Background: " + background);
                } else {
                    CraftTweakerAPI.logInfo("Created or Loaded Skill: " + name + " - With Background: " + background);
                }
            }
        }
        return customSkill;
    }

    @ZenMethod
    public static CrTSkill createSkill(String name, String backGroundLocation) {
        if (CheckMethods.checkString(name) & CheckMethods.checkString(backGroundLocation)) {
            return createSkill(new ResourceLocation(CompatSkillConstants.MOD_ID, name), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @ZenMethod
    public static CrTSkill createNewSkill(String nameLocation, String backGroundLocation) {
        if (CheckMethods.checkString(nameLocation) & CheckMethods.checkString(backGroundLocation)) {
            return createSkill(new ResourceLocation(nameLocation), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @ZenGetter("levelCap")
    @ZenMethod
    public int getLevelCap() {
        return getCap();
    }

    @ZenSetter("levelCap")
    @ZenMethod
    public void setLevelCap(int cap) {
        skillConfig.setLevelCap(cap);
    }

    @ZenGetter("enabled")
    @ZenMethod
    public boolean getEnabled() {
        return isEnabled();
    }

    //TODO make a way to set the level staggering

    @ZenSetter("enabled")
    @ZenMethod
    public void setEnabled(boolean enabled) {
        skillConfig.setEnabled(enabled);
    }

    @ZenSetter("skillPointInterval")
    @ZenMethod
    public void setSkillPointInterval(int amount) {
        skillConfig.setSkillPointInterval(amount);
    }

    @ZenGetter("baseLevelCost")
    @ZenMethod
    public int getBaseLevelCost() {
        return skillConfig.getBaseLevelCost();
    }

    @ZenSetter("baseLevelCost")
    @ZenMethod
    public void setBaseLevelCost(int cost) {
        skillConfig.setBaseLevelCost(cost);
    }

    @Override
    public String getName() {
        return name == null ? super.getName() : name.getText();
    }

    //TODO override getSpriteFromRank when we figure out a good way to say how many pictures they provide
}
