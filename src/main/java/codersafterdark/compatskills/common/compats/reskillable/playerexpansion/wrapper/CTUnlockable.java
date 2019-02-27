package codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.compatskills.Unlockable")
@ZenRegister
public class CTUnlockable {
    final Unlockable unlockable;

    public CTUnlockable(Unlockable unlockable) {
        this.unlockable = unlockable;
    }

    @ZenGetter("parent")
    @ZenMethod
    public CTSkill getParent() {
        return new CTSkill(unlockable.getParentSkill());
    }

    @ZenSetter("requirements")
    @ZenMethod
    public void setRequirements(String... requirements) {
        if (CheckMethods.checkOptionalRequirements(requirements)) {
            unlockable.getUnlockableConfig().setRequirementHolder(RequirementHolder.fromStringList(requirements));
        }
    }

    @ZenGetter("name")
    @ZenMethod
    public String getName() {
        return unlockable.getName();
    }

    @ZenGetter("getDescription")
    @ZenMethod
    public String getDescription() {
        return unlockable.getDescription();
    }

    @ZenGetter("spikes")
    @ZenMethod
    public boolean hasSpikes() {
        return unlockable.hasSpikes();
    }

    @ZenGetter("cost")
    @ZenMethod
    public int getCost() {
        return unlockable.getCost();
    }

    @ZenSetter("cost")
    @ZenMethod
    public void setCost(int cost) {
        unlockable.getUnlockableConfig().setCost(cost);
    }

    @ZenGetter("icon")
    @ZenMethod
    public String retrieveIcon() {
        return unlockable.getIcon().toString();
    }

    @ZenGetter("x")
    @ZenMethod
    public int getX() {
        return unlockable.getX();
    }

    @ZenSetter("x")
    @ZenMethod
    public void setX(int x) {
        unlockable.getUnlockableConfig().setX(x);
    }

    @ZenGetter("y")
    @ZenMethod
    public int getY() {
        return unlockable.getY();
    }

    @ZenSetter("y")
    @ZenMethod
    public void setY(int y) {
        unlockable.getUnlockableConfig().setY(y);
    }

    @ZenGetter("enabled")
    @ZenMethod
    public boolean getEnabled() {
        return unlockable.isEnabled();
    }

    @ZenSetter("enabled")
    @ZenMethod
    public void setEnabled(boolean enabled) {
        unlockable.getUnlockableConfig().setEnabled(enabled);
    }

    public Unlockable getUnlockable() {
        return unlockable;
    }
}