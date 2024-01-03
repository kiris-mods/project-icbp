package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.client.BallisticPenguinRenderer;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(ProjectICBPCommon.MOD_ID)
public class ProjectICBPNeo {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER
        = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ProjectICBPCommon.MOD_ID);
    public static final RegistryObject<EntityType<BallisticPenguin>> BALLISTIC_PENGUIN
        = ENTITY_TYPE_DEFERRED_REGISTER.register("ballistic_penguin",
        () -> EntityType.Builder.of(BallisticPenguin::new, MobCategory.CREATURE)
            //.sized(1.0f, 1.0f)
            .build("ballistic_penguin"));

    public ProjectICBPNeo() {
        ProjectICBPCommon.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerAttributes);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(this::registerRenderers);
        }
    }

    private void registerAttributes(final EntityAttributeCreationEvent event) {
        event.put(BALLISTIC_PENGUIN.get(), BallisticPenguin.createAttributes().build());
    }

    private void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BALLISTIC_PENGUIN.get(), BallisticPenguinRenderer::new);
    }
}
