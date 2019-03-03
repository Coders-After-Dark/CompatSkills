package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.registry.IFactoryData;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryTaskRequirement implements IFactoryData<ITask, NBTTagCompound> {
    public static final FactoryTaskRequirement INSTANCE = new FactoryTaskRequirement();

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(CompatSkillConstants.MOD_ID, "req_task_factory");
    }

    @Override
    public TaskRequirement createNew() {
        return new TaskRequirement();
    }

    @Override
    public TaskRequirement loadFromData(NBTTagCompound json) {
        TaskRequirement task = createNew();
        task.readFromNBT(json);
        return task;
    }
}