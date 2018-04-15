package codersafterdark.compatskills.common.compats.crafttweaker.bracketHandlers;


import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.annotations.*;
import crafttweaker.zenscript.*;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.*;

import java.util.List;
import java.util.stream.Collectors;

@BracketHandler
@ZenRegister
public class BracketHandlerSkill implements IBracketHandler {
    
    private static final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), BracketHandlerSkill.class, "getSkill", String.class);
    
    public static CTSkill getSkill(String name) {
        Skill skill = ReskillableRegistries.SKILLS.getValue(new ResourceLocation(name));
        return skill == null ? null : new CTSkill(skill);
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal iEnvironmentGlobal, List<Token> list) {
        if(list.size() <= 2 || !list.get(0).getValue().equalsIgnoreCase("skill")) {
            return null;
        }
        
        return zenPosition -> new ExpressionCallStatic(zenPosition, iEnvironmentGlobal, method, new ExpressionString(zenPosition, list.subList(0, 2).stream().map(Token::getValue).collect(Collectors.joining())));
    }
}
