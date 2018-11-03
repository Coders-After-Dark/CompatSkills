package codersafterdark.compatskills.common.compats.scavenge;

import com.google.gson.JsonObject;
import scavenge.api.autodoc.MapElement;
import scavenge.api.autodoc.TextElement;
import scavenge.api.block.IResourceProperty;
import scavenge.api.block.impl.BaseResourceFactory;

public class RequirementPropertyFactory extends BaseResourceFactory {
    public RequirementPropertyFactory() {
        super("reskillable_requirement", PropertyType.Condition);
    }

    public IResourceProperty createObject(JsonObject obj) {
        return new RequirementProperty(obj);
    }

    public void addExample(JsonObject obj) {
        obj.addProperty("requirement", "reskillable:attack|10");
    }

    public MapElement getDocumentation() {
        MapElement map = super.getDocumentation();
        map.setDescription("Allows to check if a Player meets a requirement from Reskillable");
        map.addElement((new TextElement("requirement", "")).setDescription("The requirement being checked."));
        return map;
    }
}