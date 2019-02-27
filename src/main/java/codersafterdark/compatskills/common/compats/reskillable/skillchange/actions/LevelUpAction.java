package codersafterdark.compatskills.common.compats.reskillable.skillchange.actions;

import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.IChangeHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.SkillChangeHandler;
import codersafterdark.compatskills.common.compats.reskillable.skillchange.SkillLevel;
import crafttweaker.IAction;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class LevelUpAction implements IAction {
    protected final CTSkill skill;
    protected final int level;

    private LevelUpAction(CTSkill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    SkillLevel getSkillLevel() {
        return new SkillLevel(skill.getSkill(), level);
    }

    public static class Commands extends LevelUpAction {
        private final String[] commands;

        public Commands(CTSkill skill, int level, String... commands) {
            super(skill, level);
            this.commands = commands;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(getSkillLevel(), commands);
        }

        @Override
        public String describe() {
            String descString = Arrays.stream(commands).map(string -> string + ", ").collect(Collectors.joining());
            return "Added commands to fire when " + skill.getName() + " reaches level " + level + ". Commands: " + descString;
        }
    }

    public static class Handler extends LevelUpAction {
        private final IChangeHandler handler;

        public Handler(CTSkill skill, int level, IChangeHandler handler) {
            super(skill, level);
            this.handler = handler;
        }

        @Override
        public void apply() {
            SkillChangeHandler.addSkillEvent(getSkillLevel(), handler);
        }

        @Override
        public String describe() {
            return "Added handler to fire when " + skill.getName() + " reaches level " + level + ".";
        }
    }
}