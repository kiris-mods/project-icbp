package dev.tophatcat.projecticbp.client;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class BallisticRenderingQuilt implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        EntityRendererRegistry.register(ProjectICBPCommon.BALLISTIC_PENGUIN, BallisticPenguinRenderer::new);
    }
}
