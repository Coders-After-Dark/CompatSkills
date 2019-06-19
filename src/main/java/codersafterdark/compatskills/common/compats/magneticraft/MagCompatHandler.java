package codersafterdark.compatskills.common.compats.magneticraft;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.compatskills.utils.multiblock.MultiBlockCommand;
import codersafterdark.compatskills.utils.multiblock.MultiBlockGate;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import com.cout970.magneticraft.api.MagneticraftApi;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class MagCompatHandler extends CompatModuleBase {
    public static boolean ENABLED;

    private static boolean registered;

    public static void addMagLock(MultiBlockGate key, RequirementHolder holder) {
        if (!registered) {
            MinecraftForge.EVENT_BUS.register(new MagMultiBlockHandler());
            registered = true;
        }
        LevelLockHandler.addLockByKey(key, holder);
    }

    @Override
    public void preInit() {
        ENABLED = true;
    }

    @Override
    public void loadComplete() {
        if (CompatSkills.craftweakerLoaded) {
            CompatSkills.registerCommand(new MultiBlockCommand("mag") {
                @Override
                public List<String> getMultiBlockNames() {
                    return new ArrayList<>(MagneticraftApi.getMultiblockManager().getRegisteredMultiblocks().keySet());
                }
            });
        }
    }
}