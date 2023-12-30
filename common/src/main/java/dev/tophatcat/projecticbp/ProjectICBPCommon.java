package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectICBPCommon {

    public static final String MOD_ID = "projecticbp";
    public static final String MOD_NAME = "Project ICBP";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOG.debug("We are currently loaded via the {} mod loader in a {} environment!",
            Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
    }
}
