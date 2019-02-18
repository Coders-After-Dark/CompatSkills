package codersafterdark.compatskills.common.compats.minecraft.looking;

import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.RequirementException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Map;

public class LookingAtBlockRequirement extends Requirement {
    private final IBlockState state;

    public LookingAtBlockRequirement(IBlockState state) {
        this.state = state;
        String displayInfo = state.getBlock().getLocalizedName();
        //TODO: Display some information about the properties.
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.DARK_GREEN + new TextComponentTranslation("compatskills.requirements.format.looking_at", "%s", displayInfo).getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        RayTraceResult rayTrace = Utils.getLookingAt(player);
        if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
            return state == player.getEntityWorld().getBlockState(rayTrace.getBlockPos());
        }
        return false;
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        return o instanceof LookingAtBlockRequirement && state == ((LookingAtBlockRequirement) o).state ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof LookingAtBlockRequirement && state == ((LookingAtBlockRequirement) obj).state;
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    public static LookingAtBlockRequirement fromString(String input) throws RequirementException {
        if (input.isEmpty()) {
            throw new RequirementException("No Block given.");
        }
        //block|optional properties
        String[] inputInfo = input.split("\\|");
        String blockName = inputInfo[0];
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));
        if (block == null) {
            throw new RequirementException("No Block found matching: '" + blockName + "'.");
        }
        IBlockState blockState = block.getDefaultState();
        if (inputInfo.length > 1) {
            //Treat the remaining part as the properties, use substring to allow '|' to potentially be in it
            String props = input.substring(blockName.length() + 1);
            JsonObject properties;
            try {
                properties = (JsonObject) new JsonParser().parse(props);
            } catch (JsonParseException e) {
                throw new RequirementException("Invalid properties json: '" + props + "'.");
            }
            BlockStateContainer blockStateContainer = block.getBlockState();
            for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                IProperty<?> property = blockStateContainer.getProperty(entry.getKey());
                if (property == null) {
                    throw new RequirementException("Failed to find property: '" + entry.getKey() + "'.");
                }
                blockState = setValueHelper(blockState, property, entry.getValue().getAsString());
            }
        }
        return new LookingAtBlockRequirement(blockState);
    }

    private static <T extends Comparable<T>> IBlockState setValueHelper(final IBlockState blockState, final IProperty<T> property, final String stringValue) throws RequirementException {
        return property.parseValue(stringValue).toJavaUtil().map(propertyValue -> blockState.withProperty(property, propertyValue))
                .orElseThrow(() -> new RequirementException("Failed to find value " + stringValue + " for property " + property.getName()));
    }
}