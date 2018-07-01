package codersafterdark.compatskills.common.compats.reskillable.skillchange.changelisteners;

import codersafterdark.compatskills.common.compats.reskillable.skillchange.SkillChangeHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.UnlockableLock;
import codersafterdark.reskillable.api.event.LockUnlockableEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LockHandler {
    @SubscribeEvent
    public void onUnlockableLock(LockUnlockableEvent.Post event) {
        SkillChangeHandler.handleCommands(new UnlockableLock(event.getUnlockable()), event.getEntityPlayer());
    }
}