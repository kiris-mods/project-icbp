package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.client.BallisticRenderingForge;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ProjectICBPCommon.MOD_ID)
public class ProjectICBPForge {

    private static final DeferredRegister<EntityType<?>> ENTITIES
        = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ProjectICBPCommon.MOD_ID);

    public ProjectICBPForge() {
        ProjectICBPCommon.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        setUpMobs();
        ENTITIES.register(bus);
        bus.addListener(this::registerAttributes);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(BallisticRenderingForge::registerRenderers);
        }
    }

    private void setUpMobs() {
        ProjectICBPCommon.BALLISTIC_PENGUIN = ENTITIES.register("ballistic_penguin",
            () -> EntityType.Builder.of(BallisticPenguin::new, MobCategory.CREATURE)
                .sized(0.8F, 1.5F)
                .clientTrackingRange(10)
                .build(ProjectICBPCommon.MOD_ID + ":ballistic_penguin"));
    }

    private void registerAttributes(final EntityAttributeCreationEvent event) {
        event.put(ProjectICBPCommon.BALLISTIC_PENGUIN.get(), BallisticPenguin.createAttributes().build());
    }
}
