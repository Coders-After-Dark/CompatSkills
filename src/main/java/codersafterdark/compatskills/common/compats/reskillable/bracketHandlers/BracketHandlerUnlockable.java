package codersafterdark.compatskills.common.compats.reskillable.bracketHandlers;

import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTUnlockable;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.util.ResourceLocation;
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
public class BracketHandlerUnlockable implements IBracketHandler{

    private static final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), BracketHandlerUnlockable.class, "getUnlockable", String.class);

    public static CTUnlockable getUnlockable (String name){
        Unlockable unlockable = ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(name));
        return unlockable == null ? null : new CTUnlockable(unlockable);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal iEnvironmentGlobal, List<Token> list) {
        if (list.size() <= 2 || !list.get(0).getValue().equalsIgnoreCase("unlockable")) {
            return null;
        }
        return zenPosition -> new ExpressionCallStatic(zenPosition, iEnvironmentGlobal, method, new ExpressionString(zenPosition, list.subList(0, 2).stream().map(Token::getValue).collect(Collectors.joining())));
    }
}
