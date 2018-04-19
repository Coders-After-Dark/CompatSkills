package codersafterdark.compatskills.common.compats.reskillable.levellocking;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.skill.Skill;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class SkillLockHandler {
    private Map<Skill, Map<Integer, RequirementHolder>> skillLockMap;

    public SkillLockHandler() {
        skillLockMap = Maps.newHashMap();
    }

    public void addSkillLock(SkillLock lock) {
        Skill skill = lock.getSkill();
        int level = lock.getLevel();
        RequirementHolder requirementHolder = lock.getRequirementHolder();

        if (!skillLockMap.containsKey(skill)) {
            skillLockMap.put(skill, Maps.newHashMap());
        }
        Map<Integer, RequirementHolder> levelLocks = skillLockMap.get(skill);
        levelLocks.put(level, requirementHolder);
    }

    @SubscribeEvent
    public void levelUpEvent(LevelUpEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        Skill skill = event.getSkill();
        int level = event.getLevel();
        if (skillLockMap.containsKey(skill)) {
            Map<Integer, RequirementHolder> lockMap = skillLockMap.get(skill);
            if (lockMap.containsKey(level)) {
                if (!data.matchStats(lockMap.get(level))) {
                    event.setCanceled(true);
                    String error = "To acquire this level:";
                    String error2 = "\n Please fulfill the following requirements!";
                    List<Requirement> requirements = lockMap.get(level).getRequirements();
                    StringBuilder reqString = new StringBuilder("Requirements: ");
                    for (Requirement requirement : requirements) {
                        reqString.append("\n ").append(requirement.getToolTip(data)).append(" ");
                    }
                    ITextComponent textComponent = new TextComponentString(error + error2 + reqString);
                    player.sendStatusMessage(textComponent, false);
                }
            }
        }
    }
}
