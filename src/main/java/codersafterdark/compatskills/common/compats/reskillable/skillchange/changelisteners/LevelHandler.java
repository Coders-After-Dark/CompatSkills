package codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners;

import codersafterdark.compatskills.common.compats.reskillable.skillchange.SkillChangeHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.SkillLevel;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LevelHandler {
    @SubscribeEvent
    public void levelChangeEvent(LevelUpEvent.Post event) {
        SkillChangeHandler.handleCommands(new SkillLevel(event.getSkill(), event.getLevel()), event.getEntityPlayer());
    }
}