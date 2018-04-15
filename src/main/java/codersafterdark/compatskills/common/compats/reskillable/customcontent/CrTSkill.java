package codersafterdark.compatskills.common.compats.reskillable.customcontent;


import codersafterdark.compatskills.common.compats.utils.CheckMethods;
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
    public IFormattedText name = null;
    
    @ZenMethod
    public static CrTSkill createSkill(String name, String backGroundLocation) {
        if (CheckMethods.checkString(name) & CheckMethods.checkString(backGroundLocation)){
            CraftTweakerAPI.logCommand("Created Skill: " + name + " - With Background: " + backGroundLocation);
            return new CrTSkill(new ResourceLocation(CompatSkillConstants.MOD_ID, name), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @ZenMethod
    public static CrTSkill createNewSkill(String nameLocation, String backGroundLocation){
        if (CheckMethods.checkString(nameLocation) & CheckMethods.checkString(backGroundLocation)){
            CraftTweakerAPI.logCommand("Created Skill: " + nameLocation + " - With Background: " + backGroundLocation);
            return new CrTSkill(new ResourceLocation(nameLocation), new ResourceLocation(backGroundLocation));
        }
        return null;
    }
    
    public CrTSkill(ResourceLocation name, ResourceLocation background) {
        super(name, background);
        ReskillableRegistries.SKILLS.register(this);
    }
    
    @Override
    public String getName() {
        return name == null ? super.getName() : name.getText();
    }
}
