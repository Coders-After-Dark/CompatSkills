package codersafterdark.compatskills.common.compats.betterquesting.gui;

import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.GuiAlign;
import betterquesting.api2.client.gui.misc.GuiPadding;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import codersafterdark.compatskills.common.compats.betterquesting.TaskRequirement;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class PanelTaskRequirement extends CanvasEmpty {
    private final TaskRequirement task;
    private final IQuest quest;

    public PanelTaskRequirement(IGuiRect rect, IQuest quest, TaskRequirement task) {
        super(rect);
        this.quest = quest;
        this.task = task;
    }

    @Override
    public void initPanel() {
        super.initPanel();
        List<String> tooltip = new ArrayList<>();
        task.getRequirementHolder().addRequirementsIgnoreShift(PlayerDataHandler.get(Minecraft.getMinecraft().player), tooltip);
        for (int i = 0; i < tooltip.size(); ++i) {
            this.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(0, 16 * i, 0, -16 * (i + 1)), 0), tooltip.get(i)));
        }
    }
}