package codersafterdark.compatskills.utils;

import codersafterdark.reskillable.api.data.LockKey;

import java.util.HashMap;
import java.util.Map;

public class MessageStorage {
    private static final Map<LockKey, String> failureMessages = new HashMap<>();

    public static void setFailureMessage(LockKey key, String failureMessage) {
        failureMessages.put(key, failureMessage);
    }

    public static String getFailureMessage(LockKey key) {
        return failureMessages.get(key);
    }
}