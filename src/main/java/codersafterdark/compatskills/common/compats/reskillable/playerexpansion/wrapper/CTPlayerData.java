package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
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
    @ZenMethod
    public boolean getHasAnyAbilities() {
        return playerData.hasAnyAbilities();
    }

    @ZenMethod
    public CTPlayerSkillInfo getSkillInfo(CTSkill skill) {
        if (!ReskillableCompatHandler.ENABLED) {
            return null;
        }
        PlayerSkillInfo info = playerData.getSkillInfo(skill.skill);
        return info == null ? null : new CTPlayerSkillInfo(info);
    }
}