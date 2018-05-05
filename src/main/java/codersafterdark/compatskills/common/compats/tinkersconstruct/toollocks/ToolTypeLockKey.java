package codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks;

import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.Item;

public class ToolTypeLockKey implements LockKey{
    private final Item item;


    public ToolTypeLockKey(Item item){
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof ToolTypeLockKey && item == ((ToolTypeLockKey) o).item;
    }

    @Override
    public int hashCode() {
        return item == null ? super.hashCode() : item.hashCode();
    }
}
