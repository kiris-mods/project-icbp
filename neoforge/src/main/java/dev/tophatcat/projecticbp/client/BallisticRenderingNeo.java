package dev.tophatcat.projecticbp.client;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class BallisticRenderingNeo {

    public static void registerEntityModels(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ProjectICBPCommon.BALLISTIC_PENGUIN.get(), BallisticPenguinRenderer::new);
    }
}
