package codersafterdark.compatskills.utils;

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
}