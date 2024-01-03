package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;

public class ProjectICBPFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ProjectICBPCommon.init();
        setUpMobs();
    }

    private void setUpMobs() {
        ProjectICBPCommon.BALLISTIC_PENGUIN = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin"),
            FabricEntityTypeBuilder.createMob()
                .entityFactory(BallisticPenguin::new)
                .defaultAttributes(BallisticPenguin::createAttributes)
                .dimensions(EntityDimensions.fixed(0.8F, 1.5F))
                .spawnGroup(MobCategory.CREATURE)
                .trackRangeChunks(10)
                .build());
    }
}
