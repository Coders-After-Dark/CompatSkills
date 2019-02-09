package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.stream.Collectors;

public class SkillLockHandler {
    @SubscribeEvent
    public void levelUpEvent(LevelUpEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player)) {
            return;
        }
        int level = event.getLevel();
        int oldLevel = event.getOldLevel();
        if (level < oldLevel || level > oldLevel + 1) {
            //Do not bother locking them going down in level
            //If their level changed by more than one it was by command and is ignoring locks
            return;
        }
        PlayerData data = PlayerDataHandler.get(player);
        Skill skill = event.getSkill();
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new SkillLock(skill, level));
        if (!requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.error.reskillable.level_lock");
            TextComponentTranslation error2 = new TextComponentTranslation("compatskills.error.reskillable.level_lock_2");
            TextComponentTranslation error3 = new TextComponentTranslation("compatskills.misc.requirements");
            String reqString = requirementHolder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
            ITextComponent textComponent = new TextComponentString(error.getUnformattedComponentText() + '\n' + error2.getUnformattedComponentText() + '\n' +
                    error3.getUnformattedComponentText() + ' ' + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}