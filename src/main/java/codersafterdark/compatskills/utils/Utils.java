package codersafterdark.compatskills.utils;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.RequirementHolder;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {
    public static String formatRequirements(String[] requirements) {
        if (requirements == null) {
            return "null";
        }
        String reqString = Arrays.stream(requirements).map(s -> s + ", ").collect(Collectors.joining());
        if (!reqString.isEmpty()) {
            reqString = reqString.substring(0, reqString.length() - 2);
        }
        return reqString;
    }

    public static ITextComponent getError(RequirementHolder holder, PlayerData data, TextComponentTranslation error) {
        TextComponentTranslation error2 = new TextComponentTranslation("compatskills.misc.requirements");
        String reqString = holder.getRequirements().stream().map(requirement -> "\n " + requirement.getToolTip(data) + ' ').collect(Collectors.joining());
        return new TextComponentString(error.getUnformattedComponentText() + ' ' + error2.getUnformattedComponentText() + ' ' + reqString);
    }
}