package codersafterdark.compatskills.common.compats.betterquesting.gui;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.client.gui.misc.IVolatileScreen;
import betterquesting.api.enums.EnumPacketAction;
import betterquesting.api.network.QuestingPacket;
import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.GuiScreenCanvas;
import betterquesting.api2.client.gui.controls.PanelButton;
import betterquesting.api2.client.gui.events.IPEventListener;
import betterquesting.api2.client.gui.events.PEventBroadcaster;
import betterquesting.api2.client.gui.events.PanelEvent;
import betterquesting.api2.client.gui.events.types.PEventButton;
import betterquesting.api2.client.gui.misc.GuiAlign;
import betterquesting.api2.client.gui.misc.GuiPadding;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.CanvasTextured;
import betterquesting.api2.client.gui.panels.bars.PanelVScrollBar;
import betterquesting.api2.client.gui.panels.content.PanelLine;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.client.gui.themes.presets.PresetLine;
import betterquesting.api2.client.gui.themes.presets.PresetTexture;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.QuestTranslation;
import codersafterdark.compatskills.common.compats.betterquesting.TaskRequirement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiTaskRequirementEditor extends GuiScreenCanvas implements IPEventListener, IVolatileScreen {
    private PanelScrollingStrings requirementEdit;
    private TaskRequirement task;
    private DBEntry<IQuest> quest;

    public GuiTaskRequirementEditor(GuiScreen parent, DBEntry<IQuest> quest, TaskRequirement task) {
        super(parent);
        this.quest = quest;
        this.task = task;
    }

    public void initPanel() {
        super.initPanel();

        PEventBroadcaster.INSTANCE.register(this, PEventButton.class);

        // Background panel
        CanvasTextured cvBackground = new CanvasTextured(new GuiTransform(GuiAlign.FULL_BOX, new GuiPadding(0, 0, 0, 0), 0), PresetTexture.PANEL_MAIN.getTexture());
        this.addPanel(cvBackground);

        cvBackground.addPanel(new PanelButton(new GuiTransform(GuiAlign.BOTTOM_CENTER, -100, -16, 200, 16, 0), 0, QuestTranslation.translate("gui.back")));

        PanelTextBox txTitle = new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(0, 16, 0, -32), 0), "Requirements").setAlignment(1);//TODO: Localize
        txTitle.setColor(PresetColor.TEXT_HEADER.getColor());
        cvBackground.addPanel(txTitle);

        requirementEdit = new PanelScrollingStrings(new GuiTransform(GuiAlign.FULL_BOX, new GuiPadding(16, 32, 24, 32), 0), task.getRequirements(), 1, 2);
        cvBackground.addPanel(requirementEdit);

        PanelVScrollBar scEdit = new PanelVScrollBar(new GuiTransform(GuiAlign.RIGHT_EDGE, new GuiPadding(-24, 32, 16, 32), 0));
        cvBackground.addPanel(scEdit);
        requirementEdit.setScrollDriverY(scEdit);

        // === DECORATIVE LINES ===

        IGuiRect ls0 = new GuiTransform(GuiAlign.TOP_LEFT, 16, 32, 0, 0, 0);
        IGuiRect le0 = new GuiTransform(GuiAlign.TOP_RIGHT, -16, 32, 0, 0, 0);
        drawDivider(cvBackground, ls0, le0);

        IGuiRect ls1 = new GuiTransform(GuiAlign.BOTTOM_LEFT, 16, -32, 0, 0, 0);
        IGuiRect le1 = new GuiTransform(GuiAlign.BOTTOM_RIGHT, -16, -32, 0, 0, 0);
        drawDivider(cvBackground, ls1, le1);
    }

    private void drawDivider(CanvasTextured cvBackground, IGuiRect ls, IGuiRect le) {
        ls.setParent(cvBackground.getTransform());
        le.setParent(cvBackground.getTransform());
        PanelLine paLine1 = new PanelLine(ls, le, PresetLine.GUI_DIVIDER.getLine(), 1, PresetColor.GUI_DIVIDER.getColor(), -1);
        cvBackground.addPanel(paLine1);
    }

    @Override
    public void onPanelEvent(PanelEvent event) {
        if (event instanceof PEventButton) {
            onButtonPress((PEventButton) event);
        }
    }

    private void onButtonPress(PEventButton event) {
        if (event.getButton().getButtonID() == 0) { // Exit
            mc.displayGuiScreen(this.parent);
            if (requirementEdit != null) {
                task.updateRequirements(requirementEdit.getRequirements());

                NBTTagCompound base = new NBTTagCompound();
                base.setTag("config", quest.getValue().writeToNBT(new NBTTagCompound()));
                base.setTag("progress", quest.getValue().writeProgressToNBT(new NBTTagCompound(), null));
                NBTTagCompound tags = new NBTTagCompound();
                tags.setInteger("action", EnumPacketAction.EDIT.ordinal()); // Action: Update data
                tags.setInteger("questID", quest.getID());
                tags.setTag("data", base);
                QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToServer(new QuestingPacket(new ResourceLocation("betterquesting:quest_edit"), tags));
            }
        }
    }
}