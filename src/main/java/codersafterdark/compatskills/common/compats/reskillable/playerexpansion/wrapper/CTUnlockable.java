package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.reskillable.api.unlockable.Unlockable;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.Arrays;

@ZenClass("mods.compatskills.Unlockable")
@ZenRegister
public class CTUnlockable {
    final Unlockable unlockable;

    public CTUnlockable(Unlockable unlockable) {
        this.unlockable = unlockable;
    }

    @ZenGetter
    public String getParent() {
        return unlockable.getParentSkill().getName();
    }

    @ZenGetter("getRequirements")
    public String getRequirements() {
        return Arrays.toString(unlockable.getRequirements().getRequirements().toArray());
    }

    @ZenGetter("getName")
    public String getName() {
        return unlockable.getName();
    }

    @ZenGetter("getDescription")
    public String getDescription() {
        return unlockable.getDescription();
    }

    @ZenGetter("hasSpikes")
    public boolean hasSpikes() {
        return unlockable.hasSpikes();
    }

    @ZenGetter("getCost")
    public int getCost() {
        return unlockable.getCost();
    }
}