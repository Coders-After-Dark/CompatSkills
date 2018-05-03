package codersafterdark.compatskills.common.compats.projecte;

import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.item.ItemStack;

public class ProjectECompatHandler {
    //TODO: In the future potentially support stopping entity transmutation and item transmutation instead of just allowing items to be locked by emc value
    public static void setup() {
        LevelLockHandler.registerLockKey(ItemStack.class, EMCLockKey.class);
    }
}