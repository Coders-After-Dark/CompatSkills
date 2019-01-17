package codersafterdark.compatskills.common.compats.oreexcavator.tweakers;

import codersafterdark.compatskills.common.compats.oreexcavator.ExcavationLockHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("oreexcavation")
@ZenClass("mods.compatskills.Excavation")
@ZenRegister
public class ExcavationLockTweaker {
    @ZenMethod
    public static void addExcavationLock(String... requirements) {
        if (CheckMethods.checkStringArray(requirements)) {
            ExcavationLockHandler.requirements = requirements;
        }
    }
}
