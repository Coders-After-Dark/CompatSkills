package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

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