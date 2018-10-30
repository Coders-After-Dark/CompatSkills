package codersafterdark.compatskills.common.compats.betterquesting;

import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class GuiTaskRequirement extends GuiElement implements IGuiEmbedded {
    private RequirementHolder holder;
    private Minecraft mc;
    private int posX, posY;

    public GuiTaskRequirement(TaskRequirement task, int posX, int posY, int sizeX, int sizeY) {
        holder = task.getRequirementHolder();
        mc = Minecraft.getMinecraft();
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float partialTick) {
        List<String> tooltip = new ArrayList<>();
        holder.addRequirementsIgnoreShift(PlayerDataHandler.get(mc.player), tooltip);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int row = posY;
        for (int i = 0; i < tooltip.size(); ++i) {
            fontRenderer.drawStringWithShadow(tooltip.get(i), posX, row, -1);
            if (i == 0) {
                row += 2;
            }
            row += 10;
        }
    }

    @Override
    public void drawForeground(int mx, int my, float partialTick) {

    }

    @Override
    public void onMouseClick(int mx, int my, int click) {

    }

    @Override
    public void onMouseScroll(int mx, int my, int scroll) {

    }

    @Override
    public void onKeyTyped(char c, int keyCode) {

    }
}