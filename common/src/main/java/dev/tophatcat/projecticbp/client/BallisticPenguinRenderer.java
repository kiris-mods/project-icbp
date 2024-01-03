package dev.tophatcat.projecticbp.client;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BallisticPenguinRenderer extends GeoEntityRenderer<BallisticPenguin> {

    private static final ResourceLocation resourceLocation
        = new ResourceLocation(ProjectICBPCommon.MOD_ID, "textures/ballistic_penguin/ballistic_penguin.png");

    public BallisticPenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new BallisticPenguinModel(new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin")));
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull BallisticPenguin entity) {
        return resourceLocation;
    }
}
