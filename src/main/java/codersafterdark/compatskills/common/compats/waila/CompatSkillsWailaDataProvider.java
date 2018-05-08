package codersafterdark.compatskills.common.compats.waila;

import codersafterdark.compatskills.utils.CompatSkillsConfig;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.base.LevelLockHandler;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.List;

public class CompatSkillsWailaDataProvider implements IWailaDataProvider {

    @Override
    @Nonnull
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        RequirementHolder holder = LevelLockHandler.getSkillLock(itemStack);
        if (config.getConfig("compatskills.requirements")) {
            if (CompatSkillsConfig.StargazerConfigs.Hwyla.HwylaShifting) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                    if (holder.isRealLock()) {
                        List<Requirement> requirements = holder.getRequirements();
                        EntityPlayer player = accessor.getPlayer();
                        PlayerData playerData = PlayerDataHandler.get(player);
                        TextComponentTranslation error = new TextComponentTranslation("compatskills.misc.Requirements");
                        currenttip.add(error.getFormattedText());
                        for (Requirement req : requirements) {
                            currenttip.add(req.getToolTip(playerData));
                        }
                    }
                } else {
                    if (holder.isRealLock()) {
                        TextComponentTranslation error = new TextComponentTranslation("compatskills.misc.Shift");
                        currenttip.add(error.getFormattedText());
                    }
                }
            } else if (holder.isRealLock()) {
                List<Requirement> requirements = holder.getRequirements();
                EntityPlayer player = accessor.getPlayer();
                PlayerData playerData = PlayerDataHandler.get(player);
                TextComponentTranslation error = new TextComponentTranslation("compatskills.misc.Requirements");
                currenttip.add(error.getFormattedText());
                for (Requirement req : requirements) {
                    currenttip.add(req.getToolTip(playerData));
                }
            }
        }
        return currenttip;
    }
}
