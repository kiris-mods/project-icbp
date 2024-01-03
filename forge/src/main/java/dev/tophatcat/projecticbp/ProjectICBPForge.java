package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.client.BallisticPenguinRenderer;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(ProjectICBPCommon.MOD_ID)
public class ProjectICBPForge {

    public ProjectICBPForge() {
        ProjectICBPCommon.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::registerAttributes);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(this::registerRenderers);
        }
    }

    @SubscribeEvent
    public void setUpMobs(RegisterEvent event) {
        event.register(ForgeRegistries.ENTITY_TYPES,
            new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin"),
            EntityType.Builder.of(BallisticPenguin::new, MobCategory.CREATURE)
                .sized(0.8F, 1.5F)
                .setTrackingRange(10)
                .build(ProjectICBPCommon.MOD_ID + ":ballistic_penguin"));
    }

    private void registerAttributes(final EntityAttributeCreationEvent event) {
        event.put(ProjectICBPCommon.BALLISTIC_PENGUIN, BallisticPenguin.createAttributes().build());
    }

    private void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ProjectICBPCommon.BALLISTIC_PENGUIN, BallisticPenguinRenderer::new);
    }
}
