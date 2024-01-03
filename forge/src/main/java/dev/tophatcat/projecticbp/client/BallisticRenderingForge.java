package dev.tophatcat.projecticbp.client;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class BallisticRenderingForge {

    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ProjectICBPCommon.BALLISTIC_PENGUIN.get(), BallisticPenguinRenderer::new);
    }
}
