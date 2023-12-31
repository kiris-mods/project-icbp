package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.entities.BallisticPenguinEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ProjectICBP implements ModInitializer {

    //TODO Come back here and set up the proper values.
    public static EntityType<BallisticPenguinEntity> BALLISTIC_PENGUIN
        = Registry.register(BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin"),
        FabricEntityTypeBuilder
            .create(MobCategory.CREATURE, BallisticPenguinEntity::new)
            //TODO Come back and correct the size once it's in game.
            //.dimensions(EntityDimensions.scalable(1.0f, 1.0f))
            .build());


    @Override
    public void onInitialize() {
        ProjectICBPCommon.init();
        FabricDefaultAttributeRegistry.register(BALLISTIC_PENGUIN, BallisticPenguinEntity.setUpMobAttributes());
    }
}
