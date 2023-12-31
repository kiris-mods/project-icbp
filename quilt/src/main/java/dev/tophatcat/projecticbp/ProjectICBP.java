package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.entities.BallisticPenguinEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class ProjectICBP implements ModInitializer {

    public static EntityType<BallisticPenguinEntity> BALLISTIC_PENGUIN
        = Registry.register(BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin"),
        QuiltEntityTypeBuilder.create(MobCategory.CREATURE, BallisticPenguinEntity::new)
            //TODO Come back and correct the size once it's in game.
            //.setDimensions(EntityDimensions.scalable(1.0f, 1.0f))
            .build());

    @Override
    public void onInitialize(ModContainer mod) {
        ProjectICBPCommon.init();

        //TODO Find the Quilt alternative to this
        //FabricDefaultAttributeRegistry.register(BALLISTIC_PENGUIN, BallisticPenguinEntity.setUpMobAttributes());
    }
}
