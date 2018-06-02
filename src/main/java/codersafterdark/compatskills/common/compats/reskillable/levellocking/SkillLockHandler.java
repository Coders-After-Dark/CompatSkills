package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class SkillLockHandler {

    @SubscribeEvent
    public void levelUpEvent(LevelUpEvent.Pre event) {
        int level = event.getLevel();
        int oldLevel = event.getOldLevel();
        if (level < oldLevel || level > oldLevel + 1) {
            //Do not bother locking them going down in level
            //If their level changed by more than one it was by command and is ignoring locks
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        Skill skill = event.getSkill();
        RequirementHolder requirementHolder = LevelLockHandler.getLockByKey(new SkillLock(skill, level));
        if (requirementHolder != null && !requirementHolder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(requirementHolder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.reskillable.addLevelLockError");
            TextComponentTranslation error2 = new TextComponentTranslation("compatskills.reskillable.addLevelLockError2");
            TextComponentTranslation error3 = new TextComponentTranslation("compatskills.misc.Requirements");
            List<Requirement> requirements = requirementHolder.getRequirements();
            StringBuilder reqString = new StringBuilder();
            for (Requirement requirement : requirements) {
                reqString.append("\n ").append(requirement.getToolTip(data)).append(' ');
            }
            ITextComponent textComponent = new TextComponentString(
                    error.getUnformattedComponentText() + '\n' +
                            error2.getUnformattedComponentText() + '\n' +
                            error3.getUnformattedComponentText() + ' ' + reqString);
            player.sendStatusMessage(textComponent, false);
        }
    }
}
