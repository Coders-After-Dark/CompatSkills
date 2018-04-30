package codersafterdark.compatskills.common.compats.magneticraft.handlers;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.immersiveengineering.IECompatHandler;
import codersafterdark.compatskills.common.compats.immersiveengineering.handlers.IEMultiBlockGates;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ModOnly("magneticraft")
@ZenClass("mods.compatskills.MagMultiBlockGates")
@ZenRegister
public class MagMultiBlockGates {
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
            if(CheckMethods.checkValidMultiblockNameMag(multiBlockName)){
                addToHandler(new MultiBlockGate(multiBlockName, failureMessage, defaultRequirements));
            }
        }

        public void addToHandler(MultiBlockGate multiBlockGate){
            IECompatHandler.addMultiBlockGate(multiBlockGate);
        }

        @Override
        public String describe() {
            return "Added Magneticraft MultiBlock " + this.getMultiBlockName() + " With Requirements: " + Arrays.toString(defaultRequirements);
        }

        public String getMultiBlockName() {
            return multiBlockName;
        }
    }
}
