package codersafterdark.compatskills.common.compats.immersiveengineering.handlers;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.gamestages.GameStageLocks.ActionAddTrueGameStageLock;
import codersafterdark.compatskills.common.compats.immersiveengineering.IECompatHandler;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ModOnly("immersiveengineering")
@ZenClass("mods.compatskills.IEMultiBlockGate")
@ZenRegister
public class IEMultiBlockGates {
    @ZenMethod
    public static void addGate(String multiBlockName, String failureMessage, String... defaultRequirements) {
        CompatSkills.LATE_ADDITIONS.add(new Add(multiBlockName, failureMessage, defaultRequirements));
    }

    private static class Add implements IAction {
        String multiBlockName;
        String failureMessage;
        String[] defaultRequirements;

        Add(String multiBlockName, String failureMessage, String... defaultRequirements){
            this.multiBlockName = multiBlockName;

            if (CheckMethods.checkString(failureMessage)){
                this.failureMessage = failureMessage;
            }
            if (CheckMethods.checkStringArray(defaultRequirements)){
                this.defaultRequirements = defaultRequirements;
            }
        }

        @Override
        public void apply() {
            if (CheckMethods.checkValidMultiblockNameIE(multiBlockName)){
                addToHandler(new MultiBlockGate(multiBlockName, failureMessage, defaultRequirements));
            }
        }

        public void addToHandler(MultiBlockGate multiBlockGate){
            IECompatHandler.addMultiBlockGate(multiBlockGate);
        }

        @Override
        public String describe() {
            return "Added IE MultiBlock " + this.getMultiBlockName() + " With Requirements: " + Arrays.toString(defaultRequirements);
        }

        public String getMultiBlockName() {
            return multiBlockName;
        }
    }
}
