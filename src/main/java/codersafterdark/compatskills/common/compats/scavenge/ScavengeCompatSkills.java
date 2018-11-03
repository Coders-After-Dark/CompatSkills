package codersafterdark.compatskills.common.compats.scavenge;

import net.minecraftforge.common.config.Configuration;
import scavenge.api.ScavengeAPI;
import scavenge.api.plugin.ScavengeLoaded;
import scavenge.api.plugin.ScavengePlugin;

@ScavengeLoaded(name = "CompatSkills")
public class ScavengeCompatSkills implements ScavengePlugin {
    @Override
    public boolean shouldLoad(Configuration configuration) {
        return ScavengeHandler.ENABLED;
    }

    @Override
    public void loadPlugin(ScavengeAPI registry) {
        registry.registerResourceProperty(new RequirementPropertyFactory());
    }

    @Override
    public void postLoadPlugin(ScavengeAPI registry) {
    }
}