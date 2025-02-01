/*
 * Oh look, a cute friendly penguin... OH! LOOK, A PENGUIN CHARGING AT US!!! Project Intercontinental Ballistic Penguin!
 * Copyright (C) KiriCattus 2013 - 2025
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

import dev.tophatcat.projecticbp.platform.IPlatformHelper;
import dev.tophatcat.projecticbp.registry.BallisticCreativeTabRegistry;
import dev.tophatcat.projecticbp.registry.BallisticEntityRegistry;
import dev.tophatcat.projecticbp.registry.BallisticItemRegistry;
import dev.tophatcat.projecticbp.registry.BallisticMemoryTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.ServiceLoader;

public class ProjectICBPCommon {

    public static final String MOD_ID = "projecticbp";
    public static final IPlatformHelper COMMON_PLATFORM = ServiceLoader.load(IPlatformHelper.class).findFirst().orElseThrow();

    public static final TagKey<Biome> SPAWN_BALLISTIC_PENGUIN = TagKey.create(Registries.BIOME,
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "allows_surface_penguin_spawns"));

    public static void init() {
        BallisticEntityRegistry.init();
        BallisticItemRegistry.init();
        BallisticCreativeTabRegistry.init();
        BallisticMemoryTypes.init();
    }
}
