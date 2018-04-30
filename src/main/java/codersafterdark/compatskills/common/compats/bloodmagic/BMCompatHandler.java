package codersafterdark.compatskills.common.compats.bloodmagic;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import codersafterdark.compatskills.common.compats.bloodmagic.BindHandler.BindHandler;
import codersafterdark.compatskills.common.compats.bloodmagic.RitualHandler.RitualHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class BMCompatHandler {
    private static BindHandler bindHandler;
    private static RitualHandler ritualHandler;

    public static void setup() {
        bindHandler = new BindHandler();
        MinecraftForge.EVENT_BUS.register(bindHandler);
        ritualHandler = new RitualHandler();
        MinecraftForge.EVENT_BUS.register(ritualHandler);

        CTChatCommand.registerCommand(new RitualCommand() {
            @Override
            public List<String> getRitualNames() {
                return new ArrayList<>(RitualRegistry.getIds());
            }
        });
    }

    public static void addBindLock(String failureMessage, ItemStack stack, RequirementHolder holder){
        bindHandler.setFailureMessage(failureMessage);
        bindHandler.addBindHolder(stack, holder);
    }

    public static void addRitualLock(String failureMessage, Ritual ritual, RequirementHolder holder){
        ritualHandler.setFailureMessage(failureMessage);
        ritualHandler.addRitualHolder(ritual, holder);
    }
}
