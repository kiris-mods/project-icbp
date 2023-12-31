package dev.tophatcat.projecticbp.client;

import dev.tophatcat.projecticbp.ProjectICBP;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FabricBallisticRendering implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ProjectICBP.BALLISTIC_PENGUIN, BallisticPenguinRenderer::new);
    }
}
