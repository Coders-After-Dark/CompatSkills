package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.MessageStorage;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("immersiveengineering")
@ZenClass("mods.compatskills.IEMultiBlockGate")
@ZenRegister
public class IEMultiBlockGates {
    @ZenMethod
    public static void addGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        if (CheckMethods.checkString(failureMessage) && CheckMethods.checkStringArray(defaultRequirements)) {
            StringBuilder descString = new StringBuilder("Requirements: ");

            for (String string : defaultRequirements) {
                descString.append(string).append(", ");
            }

            CraftTweakerAPI.apply(new IAction() {
                @Override
                public void apply() {
                    RequirementHolder holder = RequirementHolder.fromStringList(defaultRequirements);
                    MultiBlockGate gate = new MultiBlockGate(multiBlockName);
                    LevelLockHandler.addLockByKey(gate, holder);
                    MessageStorage.setFailureMessage(gate, failureMessage);
                }

                @Override
                public String describe() {
                    return "Added MultiBlock " + multiBlockName + " With Requirements: " + descString;
                }
            });
        }
    }
}
