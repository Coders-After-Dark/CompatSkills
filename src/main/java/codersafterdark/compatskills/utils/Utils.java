package codersafterdark.compatskills.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {
    public static String formatRequirements(String[] requirements) {
        return requirements == null ? "null" : Arrays.stream(requirements).map(s -> s + ", ").collect(Collectors.joining());
    }
}