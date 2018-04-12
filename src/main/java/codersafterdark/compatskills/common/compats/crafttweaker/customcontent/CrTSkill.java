package codersafterdark.compatskills.common.compats.crafttweaker.customcontent;


import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.reskillable.Skill")
@ZenRegister
public class CrTSkill extends Skill {
    
    @ZenProperty
    public IFormattedText name = null;
    
    @ZenMethod
    public static CrTSkill create(String nameLocation, String backGroundLocation) {
        return new CrTSkill(new ResourceLocation(nameLocation), new ResourceLocation(backGroundLocation));
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
