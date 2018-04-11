package codersafterdark.compatskills.common.compats.crafttweaker;

import codersafterdark.compatskills.common.compats.crafttweaker.wrapper.CTPlayerData;
import codersafterdark.reskillable.api.data.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class PlayerExpansion {
    
    @ZenGetter("skillData")
    public static CTPlayerData getSkillData(IPlayer iPlayer) {
        PlayerData playerData = PlayerDataHandler.get(CraftTweakerMC.getPlayer(iPlayer));
        return playerData == null ? null : new CTPlayerData(playerData);
    }
}
