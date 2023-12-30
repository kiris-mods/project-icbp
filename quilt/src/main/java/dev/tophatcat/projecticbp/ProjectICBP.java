package dev.tophatcat.projecticbp;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class ProjectICBP implements ModInitializer {

    @Override
    public void onInitialize(ModContainer mod) {
        ProjectICBPCommon.init();
    }
}
