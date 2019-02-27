package codersafterdark.compatskills.common.compats.reskillable.skillchange;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.compatskills.IChangeHandler")
public interface IChangeHandler {
    void handleChange(IPlayer player);
}