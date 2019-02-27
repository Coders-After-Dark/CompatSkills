package codersafterdark.compatskills.common.compats.reskillable.skillchange.actions;

import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTUnlockable;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.*;
import crafttweaker.IAction;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class UnlockableAction implements IAction {
    protected final CTUnlockable unlockable;
    protected final boolean unlock;

    private UnlockableAction(CTUnlockable unlockable, boolean unlock) {
        this.unlockable = unlockable;
        this.unlock = unlock;
    }

    UnlockableChange getUnlockableChange() {
        return unlock ? new UnlockableUnlock(unlockable.getUnlockable()) : new UnlockableLock(unlockable.getUnlockable());
    }

    public static class Commands extends UnlockableAction {
        private final String[] commands;

        public Commands(CTUnlockable unlockable, String[] commands, boolean unlock) {
            super(unlockable, unlock);
            this.commands = commands;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(getUnlockableChange(), commands);
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(commands).map(string -> string + ", ").collect(Collectors.joining());
            return "Added commands to fire when " + unlockable.getName() + " is " +  (unlock ? "unlocked" : "locked") + ". Commands: " + descString;
        }
    }

    public static class Handler extends UnlockableAction {
        private final IChangeHandler handler;

        public Handler(CTUnlockable unlockable, IChangeHandler handler, boolean unlock) {
            super(unlockable, unlock);
            this.handler = handler;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(getUnlockableChange(), handler);
        }

        @Override
        public String describe() {
            return "Added handler to fire when " + unlockable.getName() + " is " +  (unlock ? "unlocked" : "locked") + ".";
        }
    }
}