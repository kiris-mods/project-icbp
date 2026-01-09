/*
 * Oh look, a cute friendly penguin... OH! LOOK, A PENGUIN CHARGING AT US!!! Project Intercontinental Ballistic Penguin!
 * Copyright (C) KiriCattus 2013 - 2026
 * https://github.com/kiris-mods/project-icbp/blob/dev/LICENSE.md
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package dev.tophatcat.projecticbp;

import dev.tophatcat.projecticbp.entities.BallisticPenguinEntity;
import dev.tophatcat.projecticbp.registry.BallisticEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ProjectICBPFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ProjectICBP.init();
        BallisticEntities.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
        setupSpawns();
    }

    private void setupSpawns() {
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().is(
                ProjectICBP.SPAWN_BALLISTIC_PENGUIN), MobCategory.CREATURE, BallisticEntities.BALLISTIC_PENGUIN.get(),
            30, 2, 3);
        SpawnPlacements.register(BallisticEntities.BALLISTIC_PENGUIN.get(), SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BallisticPenguinEntity::checkSpawnRules);
    }
}
