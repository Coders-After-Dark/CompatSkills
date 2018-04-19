package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;


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

    @ZenMethod
    public void levelUp() {
        playerSkillInfo.levelUp();
    }

    @ZenMethod
    public void respec() {
        playerSkillInfo.respec();
    }

    @ZenMethod
    public void unlock(CTUnlockable ctUnlockable, IPlayer player) {
        Unlockable u = ctUnlockable.unlockable;
        EntityPlayer p = CraftTweakerMC.getPlayer(player);
        playerSkillInfo.unlock(u, p);
    }
}
