package codersafterdark.compatskills.utils;

import net.minecraft.util.text.TextComponentTranslation;

public class CompatSkillConstants {
    public static final String MOD_ID = "compatskills";
    public static final String MOD_NAME = "CompatSkills";
    public static final String VERSION = "1.12.2-1.9.0";
    public static final String DEPENDENCIES = "required-after:reskillable@[1.8.0,)";
    public static final String MCVER = "1.12,";

    //Proxy Constants
    public static final String PROXY_COMMON = "codersafterdark.compatskills.common.CommonProxy";
    public static final String PROXY_CLIENT = "codersafterdark.compatskills.client.ClientProxy";

    public static final String REQUIREMENT_STRING = '\n' + new TextComponentTranslation("compatskills.misc.Requirements").getUnformattedComponentText() + ' ';
}