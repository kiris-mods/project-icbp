package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.client.BallisticRenderingNeo;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ProjectICBPCommon.MOD_ID)
public class ProjectICBPNeo {

    private static final DeferredRegister<EntityType<?>> ENTITIES
        = DeferredRegister.create(Registries.ENTITY_TYPE, ProjectICBPCommon.MOD_ID);

    public ProjectICBPNeo(IEventBus bus) {
        ProjectICBPCommon.init();
        setUpMobs();
        ENTITIES.register(bus);
        bus.addListener(this::registerAttributes);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(BallisticRenderingNeo::registerEntityModels);
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
