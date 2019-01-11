package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.api.QuestingAPI;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRequirement implements ITask {
    private final List<UUID> completeUsers = new ArrayList<>();
    private List<String> requirements = new ArrayList<>();
    private RequirementHolder holder;
    private boolean hasUncacheable;

    @Override
    public String getUnlocalisedName() {
        return "compatskills.betterquesting.requirementtask";
    }

    @Override
    public ResourceLocation getFactoryID() {
        return FactoryTaskRequirement.INSTANCE.getRegistryName();
    }

    @Override
    public void detect(EntityPlayer player, IQuest quest) {
        if (isComplete(QuestingAPI.getQuestingUUID(player))) {
            return;
        }

        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            return;
        }
        setHolder();

        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            setComplete(QuestingAPI.getQuestingUUID(player));
        }
    }

    private void setHolder() {
        if (holder == null) { //May not have been initialized yet
            List<Requirement> reqs = new ArrayList<>();
            for (String req : requirements) {
                Requirement requirement = ReskillableAPI.getInstance().getRequirementRegistry().getRequirement(req);
                if (requirement != null) {
                    if (!requirement.isCacheable()) {
                        hasUncacheable = true;
                    }
                    reqs.add(requirement);
                }
            }
            holder = new RequirementHolder(reqs);
        }
    }

    @Override
    public boolean isComplete(UUID uuid) {
        return completeUsers.contains(uuid);
    }

    @Override
    public void setComplete(UUID uuid) {
        if (!completeUsers.contains(uuid)) {
            completeUsers.add(uuid);
        }
    }

    @Override
    public void resetUser(UUID uuid) {
        completeUsers.remove(uuid);
    }

    @Override
    public void resetAll() {
        completeUsers.clear();
    }

    @Override
    public IJsonDoc getDocumentation() {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IGuiPanel getTaskGui(IGuiRect rect, IQuest quest) {
        return new PanelTaskRequirement(rect, quest, this);
    }

    @Nullable
    @Override
    public GuiScreen getTaskEditor(GuiScreen parent, IQuest quest) {
        //TODO
        return null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound json) {
        NBTTagList reqs = new NBTTagList();
        for (String req : requirements) {
            reqs.appendTag(new NBTTagString(req));
        }
        json.setTag("requirements", reqs);
        return json;
    }

    @Override
    public void readFromNBT(NBTTagCompound json) {
        requirements.clear();
        NBTTagList reqs = json.getTagList("requirements", Constants.NBT.TAG_STRING);
        for (NBTBase req : reqs) {
            if (req instanceof NBTTagString) {
                requirements.add(((NBTTagString) req).getString());
            }
        }
        //TODO should this set holder as null to ensure it rechecks about cacheable requirements?
        setHolder();
    }

    @Override
    public NBTTagCompound writeProgressToNBT(NBTTagCompound json, List<UUID> users) {
        NBTTagList jArray = new NBTTagList();
        for (UUID uuid : completeUsers) {
            jArray.appendTag(new NBTTagString(uuid.toString()));
        }
        json.setTag("completeUsers", jArray);
        return json;
    }

    @Override
    public void readProgressFromNBT(NBTTagCompound json, boolean merge) {
        completeUsers.clear();
        NBTTagList cList = json.getTagList("completeUsers", 8);
        for (int i = 0; i < cList.tagCount(); i++) {
            try {
                completeUsers.add(UUID.fromString(cList.getStringTagAt(i)));
            } catch (Exception e) {
                CompatSkills.logger.log(Level.ERROR, "Unable to load UUID for task", e);
            }
        }
    }

    public RequirementHolder getRequirementHolder() {
        setHolder();
        return holder;
    }

    public boolean hasUncacheable() {
        return hasUncacheable;
    }
}