package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;


@ZenRegister
@ZenClass("mods.compatskills.PlayerSkillInfo")
public class CTPlayerSkillInfo {
    
    private final PlayerSkillInfo playerSkillInfo;
    
    public CTPlayerSkillInfo(PlayerSkillInfo playerSkillInfo) {
        this.playerSkillInfo = playerSkillInfo;
    }
    
    
    @ZenGetter("level")
    public int getLevel() {
        return playerSkillInfo.getLevel();
    }
    
    @ZenGetter("skillPoints")
    public int getSkillPoints() {
        return playerSkillInfo.getSkillPoints();
    }
    
    @ZenGetter("levelUpCost")
    public int getLevelUpCost() {
        return playerSkillInfo.getLevelUpCost();
    }
    
    @ZenGetter("rank")
    public int getRank() {
        return playerSkillInfo.getRank();
    }
    
    @ZenGetter("skill")
    public CTSkill getSkill() {
        return new CTSkill(playerSkillInfo.skill);
    }
    
    
}
