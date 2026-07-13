package codersafterdark.compatskills.common.compats.reskillable.playerexpansion;

import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTPlayerData;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class PlayerExpansion {
    @ZenGetter("skillData")
    public static CTPlayerData getSkillData(IPlayer iPlayer) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        PlayerData playerData = PlayerDataHandler.get(player);
        return playerData == null ? null : new CTPlayerData(playerData, player);
    }
}