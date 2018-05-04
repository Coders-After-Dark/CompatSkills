package codersafterdark.compatskills.common.compats.tinkersconstruct.toollocks;

import codersafterdark.reskillable.api.data.LockKey;
import net.minecraft.item.Item;

public class ToolTypeLockKey implements LockKey{
    private final String id;

    public ToolTypeLockKey(String id){
        this.id = id;
    }

    public ToolTypeLockKey(Item item){
        this(item == null ? "" : item.getUnlocalizedName());
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ToolTypeLockKey && id.equals(((ToolTypeLockKey) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
