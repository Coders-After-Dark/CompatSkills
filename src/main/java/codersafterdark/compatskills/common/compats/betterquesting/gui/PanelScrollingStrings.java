package codersafterdark.compatskills.common.compats.betterquesting.gui;

import betterquesting.api2.client.gui.controls.IPanelButton;
import betterquesting.api2.client.gui.controls.PanelButton;
import betterquesting.api2.client.gui.controls.PanelButtonStorage;
import betterquesting.api2.client.gui.controls.PanelTextField;
import betterquesting.api2.client.gui.controls.filters.FieldFilterString;
import betterquesting.api2.client.gui.events.IPEventListener;
import betterquesting.api2.client.gui.events.PEventBroadcaster;
import betterquesting.api2.client.gui.events.PanelEvent;
import betterquesting.api2.client.gui.events.types.PEventButton;
import betterquesting.api2.client.gui.misc.GuiRectangle;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.lists.CanvasScrolling;
import betterquesting.api2.client.gui.resources.colors.GuiColorStatic;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelScrollingStrings extends CanvasScrolling implements IPEventListener {
    private final GuiColorStatic red = new GuiColorStatic(255, 0, 0, 255);
    private final GuiColorStatic green = new GuiColorStatic(0, 255, 0, 255);
    private final int btnNew;
    private final int btnRemove;

    private Map<Integer, Row> requirements = new HashMap<>();
    private List<String> initialRequirements = new ArrayList<>();
    private PanelButton add;
    private int width, index;

    public PanelScrollingStrings(IGuiRect rect, List<String> requirements, int btnNew, int btnRemove) {
        super(rect);
        this.btnNew = btnNew;
        this.btnRemove = btnRemove;

        PEventBroadcaster.INSTANCE.register(this, PEventButton.class);
        Keyboard.enableRepeatEvents(true);

        for (String req : requirements) {
            if (!req.isEmpty()) {
                this.initialRequirements.add(req);
            }
        }
        this.initialRequirements.add("");
    }

    public List<String> getRequirements() {
        List<String> reqs = new ArrayList<>();
        for (Row row : this.requirements.values()) {
            String req = row.textBox.getValue();
            if (!req.isEmpty()) {
                reqs.add(req);
            }
        }
        return reqs;
    }

    @Override
    public void initPanel() {
        super.initPanel();

        this.width = getTransform().getWidth();

        this.initialRequirements.forEach(this::addRow);
        shiftAdd();
    }

    private void redraw() {
        List<String> reqs = new ArrayList<>();
        for (Row row : this.requirements.values()) {
            reqs.add(row.textBox.getValue());
            this.removePanel(row.textBox);
            this.removePanel(row.remove);
        }
        this.requirements.clear();
        this.index = 0;
        reqs.forEach(this::addRow);
        shiftAdd();
    }

    private void addRow(String line) {
        int i = this.requirements.size() + 1;
        int key = this.index++;

        PanelTextField<String> text = new PanelTextField<>(new GuiRectangle(0, i * 16, this.width - 32, 16), line, FieldFilterString.INSTANCE);
        this.addPanel(text);

        PanelButtonStorage<Integer> remove = new PanelButtonStorage<>(new GuiRectangle(this.width - 16, i * 16, 16, 16), this.btnRemove, "x", key);
        remove.setTextHighlight(PresetColor.BTN_DISABLED.getColor(), this.red, this.red);
        this.addPanel(remove);

        this.requirements.put(key, new Row(text, remove));
    }

    private void shiftAdd() {
        if (this.add != null) {
            this.removePanel(this.add);
        }

        this.add = new PanelButton(new GuiRectangle(this.width - 32, this.requirements.size() * 16, 16, 16), this.btnNew, "+");
        this.add.setTextHighlight(PresetColor.BTN_DISABLED.getColor(), this.green, this.green);
        this.addPanel(this.add);
    }

    @Override
    public void onPanelEvent(PanelEvent event) {
        if (event instanceof PEventButton) {
            onButtonPress((PEventButton) event);
        }
    }

    private void onButtonPress(PEventButton event) {
        IPanelButton btn = event.getButton();
        if (btn.getButtonID() == this.btnNew) {
            addRow("");
            shiftAdd();
        } else if (btn.getButtonID() == this.btnRemove) {
            int key = ((PanelButtonStorage<Integer>) btn).getStoredValue();
            Row row = this.requirements.get(key);
            if (this.requirements.size() == 1) {//Clear it instead of removing it
                row.textBox.setText("");
            } else {
                this.removePanel(row.remove);
                this.removePanel(row.textBox);
                this.requirements.remove(key);
                redraw();
            }
        }
    }

    private class Row {
        private PanelTextField<String> textBox;
        private PanelButtonStorage<Integer> remove;

        private Row(PanelTextField<String> textBox, PanelButtonStorage<Integer> remove) {
            this.textBox = textBox;
            this.remove = remove;
        }
    }
}