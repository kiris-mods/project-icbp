package dev.tophatcat.projecticbp;

import com.google.common.base.Suppliers;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class ProjectICBPQuilt implements ModInitializer {

    @Override
    public void onInitialize(ModContainer mod) {
        ProjectICBPCommon.init();
        ProjectICBPCommon.LOG.debug("HELLO FROM DEV!!!");
        setUpMobs();
    }

    private void setUpMobs() {
        ProjectICBPCommon.BALLISTIC_PENGUIN = Suppliers.ofInstance(Registry.register(BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(ProjectICBPCommon.MOD_ID, "ballistic_penguin"),
            QuiltEntityTypeBuilder.createMob()
                .entityFactory(BallisticPenguin::new)
                .defaultAttributes(BallisticPenguin.createAttributes())
                .setDimensions(EntityDimensions.fixed(0.8F, 1.5F))
                .spawnGroup(MobCategory.CREATURE)
                .maxChunkTrackingRange(10)
                .build()));
    }
}
