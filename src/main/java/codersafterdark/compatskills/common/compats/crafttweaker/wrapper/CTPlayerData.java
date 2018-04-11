package codersafterdark.compatskills.common.compats.crafttweaker.wrapper;

import codersafterdark.reskillable.api.data.*;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.compatskills.playerData")
@ZenRegister
public class CTPlayerData {
    private final PlayerData playerData;
    
    public CTPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
    
    @ZenGetter("hasAnyAbilities")
    public boolean getHasAnyAbilities() {
        return playerData.hasAnyAbilities();
    }
    
    @ZenMethod
    public CTPlayerSkillInfo getSkillInfo(CTSkill skill) {
        PlayerSkillInfo info = playerData.getSkillInfo(skill.skill);
        return info == null ? null : new CTPlayerSkillInfo(info);
    }
    
}
