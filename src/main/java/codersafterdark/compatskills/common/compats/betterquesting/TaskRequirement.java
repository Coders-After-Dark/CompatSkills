package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;
import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.betterquesting.gui.GuiTaskRequirementEditor;
import codersafterdark.compatskills.common.compats.betterquesting.gui.PanelTaskRequirement;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

public class TaskRequirement implements ITask {

    private final Set<UUID> completeUsers = new TreeSet<>();
    private List<String> requirements = new ArrayList<>();
    private RequirementHolder holder;
    private boolean hasUncacheable;

    @Override
    public String getUnlocalisedName() {
        return "compatskills.misc.betterquesting.requirement_task";
    }

    @Override
    public ResourceLocation getFactoryID() {
        return FactoryTaskRequirement.INSTANCE.getRegistryName();
    }

    @Override
    public void detect(ParticipantInfo participant, DBEntry<IQuest> quest) {
        if (isComplete(participant.UUID)) {
            return;
        }

        PlayerData data = PlayerDataHandler.get(participant.PLAYER);
        if (data == null) {
            return;
        }
        setHolder();

        if (data.matchStats(holder)) {
            setComplete(participant.UUID);
        }
        participant.markDirty(Collections.singletonList(quest.getID()));
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
        completeUsers.add(uuid);
    }

    @Override
    public void resetUser(@Nullable UUID uuid) {
        if (uuid == null) {
            completeUsers.clear();
        } else {
            completeUsers.remove(uuid);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IGuiPanel getTaskGui(IGuiRect rect, DBEntry<IQuest> quest) {
        return new PanelTaskRequirement(rect, this);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getTaskEditor(GuiScreen parent, DBEntry<IQuest> quest) {
        return new GuiTaskRequirementEditor(parent, quest, this);
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
        resetHolder();
    }

    private void resetHolder() {
        holder = null;
        hasUncacheable = false;
        setHolder();
    }

    @Override
    public NBTTagCompound writeProgressToNBT(NBTTagCompound json, @Nullable List<UUID> users) {
        NBTTagList jArray = new NBTTagList();
        completeUsers.stream().filter(completeUser -> users == null || users.contains(completeUser)).map(completeUser -> new NBTTagString(completeUser.toString()))
              .forEach(jArray::appendTag);
        json.setTag("completeUsers", jArray);
        return json;
    }

    @Override
    public void readProgressFromNBT(NBTTagCompound json, boolean merge) {
        if (!merge) {
            completeUsers.clear();
        }
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

    public List<String> getRequirements() {
        return requirements;
    }

    public void updateRequirements(List<String> requirements) {
        this.requirements = requirements;
        resetHolder();
    }

    public boolean hasUncacheable() {
        return hasUncacheable;
    }
}