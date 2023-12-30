package dev.tophatcat.projecticbp;

import net.fabricmc.api.ModInitializer;

public class ProjectICBP implements ModInitializer {

    @Override
    public void onInitialize() {
        ProjectICBPCommon.init();
    }
}
