package codersafterdark.compatskills.common.compats.reskillable.brackethandlers;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;

import java.util.List;
import java.util.stream.Collectors;

@BracketHandler
@ZenRegister
public class BracketHandlerSkill implements IBracketHandler {
    private static final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), BracketHandlerSkill.class, "getSkill", String.class);

    public static CTSkill getSkill(String name) {
        if (!ReskillableCompatHandler.ENABLED) {
            return null;
        }
        Skill skill = ReskillableRegistries.SKILLS.getValue(new ResourceLocation(name));
        CompatSkills.logger.log(Level.INFO, name);
        return skill == null ? null : new CTSkill(skill);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal iEnvironmentGlobal, List<Token> list) {
        if (list.size() <= 2 || !list.get(0).getValue().equalsIgnoreCase("skill")) {
            return null;
        }

        return zenPosition -> new ExpressionCallStatic(zenPosition, iEnvironmentGlobal, method, new ExpressionString(zenPosition, list.subList(2, list.size()).stream().map(Token::getValue).collect(Collectors.joining())));
    }
}