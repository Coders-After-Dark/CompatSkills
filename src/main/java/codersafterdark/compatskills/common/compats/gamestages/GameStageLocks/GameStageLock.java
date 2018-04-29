package codersafterdark.compatskills.common.compats.gamestages.GameStageLocks;

import codersafterdark.reskillable.api.data.LockKey;

public class GameStageLock implements LockKey {
    private final String gamestage;

    public GameStageLock(String gamestage){
        this.gamestage = gamestage;
    }

    public String getGamestage() {
        return gamestage;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof GameStageLock && gamestage.equals(((GameStageLock) o).gamestage);
    }

    @Override
    public int hashCode() {
        return gamestage.hashCode();
    }
}
