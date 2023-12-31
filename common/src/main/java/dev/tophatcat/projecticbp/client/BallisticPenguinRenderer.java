package dev.tophatcat.projecticbp.client;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import dev.tophatcat.projecticbp.entities.BallisticPenguinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BallisticPenguinRenderer extends GeoEntityRenderer<BallisticPenguinEntity> {

    private static final ResourceLocation resourceLocation
        = new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin");

    public BallisticPenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new BallisticPenguinModel(resourceLocation));
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(BallisticPenguinEntity entity) {
        return new ResourceLocation(ProjectICBPCommon.MOD_ID,
            "ballistic_penguin.png");
    }
}
