package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.api.event.LockUnlockableEvent;
import codersafterdark.reskillable.api.event.UnlockUnlockableEvent;
import codersafterdark.reskillable.api.toast.ToastHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.compatskills.PlayerSkillInfo")
public class CTPlayerSkillInfo {
    private final PlayerSkillInfo playerSkillInfo;
    private final PlayerData playerData;
    private final EntityPlayer player;

    public CTPlayerSkillInfo(PlayerSkillInfo playerSkillInfo, PlayerData playerData, EntityPlayer player) {
        this.playerSkillInfo = playerSkillInfo;
        this.playerData = playerData;
        this.player = player;
    }

    @ZenGetter("level")
    @ZenMethod
    public int getLevel() {
        return playerSkillInfo.getLevel();
    }

    @ZenSetter("level")
    @ZenMethod
    public void setLevel(int level) {
        if (player instanceof EntityPlayerMP) {
            if (level < 1) {
                CraftTweakerAPI.logError(new TextComponentTranslation("reskillable.command.invalid.belowmin", level).getUnformattedComponentText());
            }
            if (level > playerSkillInfo.skill.getCap()) {
                CraftTweakerAPI.logError(new TextComponentTranslation("reskillable.command.invalid.pastcap", level, playerSkillInfo.skill.getCap()).getUnformattedComponentText());
            }
            int oldLevel = playerSkillInfo.getLevel();
            if (!MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Pre(player, playerSkillInfo.skill, level, oldLevel))) {
                playerSkillInfo.setLevel(level);
                playerData.saveAndSync();
                MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Post(player, playerSkillInfo.skill, level, oldLevel));
                ToastHelper.sendSkillToast((EntityPlayerMP) player, playerSkillInfo.skill, level);
            }
        }
    }

    @ZenGetter("skillPoints")
    @ZenMethod
    public int getSkillPoints() {
        return playerSkillInfo.getSkillPoints();
    }

    @ZenGetter("levelUpCost")
    @ZenMethod
    public int getLevelUpCost() {
        return playerSkillInfo.getLevelUpCost();
    }

    @ZenGetter("rank")
    @ZenMethod
    public int getRank() {
        return playerSkillInfo.getRank();
    }

    @ZenGetter("capped")
    @ZenMethod
    public boolean isCapped() {
        return playerSkillInfo.isCapped();
    }

    @ZenGetter("skill")
    @ZenMethod
    public CTSkill getSkill() {
        return new CTSkill(playerSkillInfo.skill);
    }

    @ZenMethod
    public void levelUp() {
        if (player instanceof EntityPlayerMP) {
            int oldLevel = playerSkillInfo.getLevel();
            if (oldLevel >= playerSkillInfo.skill.getCap()) {
                CraftTweakerAPI.logError(new TextComponentTranslation("reskillable.command.invalid.pastcap", oldLevel, playerSkillInfo.skill.getCap()).getUnformattedComponentText());
            }
            if (!MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Pre(player, playerSkillInfo.skill, oldLevel + 1, oldLevel))) {
                playerSkillInfo.levelUp();
                playerData.saveAndSync();
                MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Post(player, playerSkillInfo.skill, playerSkillInfo.getLevel(), oldLevel));
                ToastHelper.sendSkillToast((EntityPlayerMP) player, playerSkillInfo.skill, playerSkillInfo.getLevel());
            }
        }
    }

    @ZenMethod
    public void respec() {
        if (player instanceof EntityPlayerMP) {
            int oldLevel = playerSkillInfo.getLevel();
            if (!MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Pre(player, playerSkillInfo.skill, 1, oldLevel))) {
                playerSkillInfo.setLevel(1);
                playerSkillInfo.respec();
                playerData.saveAndSync();
                MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Post(player, playerSkillInfo.skill, 1, oldLevel));
                ToastHelper.sendSkillToast((EntityPlayerMP) player, playerSkillInfo.skill, 1);
            }
        }
    }

    @ZenMethod
    public void unlock(CTUnlockable ctUnlockable) {
        if (player instanceof EntityPlayerMP) {
            if (!MinecraftForge.EVENT_BUS.post(new UnlockUnlockableEvent.Pre(player, ctUnlockable.unlockable))) {
                playerSkillInfo.unlock(ctUnlockable.unlockable, player);
                playerData.saveAndSync();
                MinecraftForge.EVENT_BUS.post(new UnlockUnlockableEvent.Post(player, ctUnlockable.unlockable));
            }
        }
    }

    @ZenMethod
    public void lock(CTUnlockable ctUnlockable) {
        if (player instanceof EntityPlayerMP) {
            if (!MinecraftForge.EVENT_BUS.post(new LockUnlockableEvent.Pre(player, ctUnlockable.unlockable))) {
                playerSkillInfo.lock(ctUnlockable.unlockable, player);
                playerData.saveAndSync();
                MinecraftForge.EVENT_BUS.post(new LockUnlockableEvent.Post(player, ctUnlockable.unlockable));
            }
        }
    }

    @ZenMethod
    public boolean isUnlocked(CTUnlockable ctUnlockable) {
        return playerSkillInfo.isUnlocked(ctUnlockable.unlockable);
    }
}