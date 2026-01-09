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

import com.mojang.logging.LogUtils;
import dev.tophatcat.projecticbp.registry.BallisticCreativeTab;
import dev.tophatcat.projecticbp.registry.BallisticEntities;
import dev.tophatcat.projecticbp.registry.BallisticItems;
import dev.tophatcat.projecticbp.registry.BallisticMemoryTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.slf4j.Logger;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ServiceLoader;

public class ProjectICBP {

    public static final String MOD_ID = "project_icbp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Biome> SPAWN_BALLISTIC_PENGUIN = TagKey.create(Registries.BIOME,
        Identifier.fromNamespaceAndPath(MOD_ID, "allows_surface_penguin_spawns"));

    public static void init() {
        loadClass(BallisticCreativeTab.class);
        loadClass(BallisticEntities.class);
        loadClass(BallisticItems.class);
        loadClass(BallisticMemoryTypes.class);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static <T> T loadService(Class<T> serviceClass) {
        return ServiceLoader.load(serviceClass, serviceClass.getClassLoader()).findFirst()
            .orElseThrow(() -> new NoSuchElementException("Unable to find implementation service for " + serviceClass.getName()));
    }

    private static void loadClass(Class<?> clazz) {
        var mask = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
        var count = Arrays.stream(clazz.getDeclaredFields()).filter(field -> (field.getModifiers() | mask) == mask).map(field -> {
                try {
                    return field.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Access error while registering %s from %s".formatted(field.getName(), clazz.getName()), e);
                }
            })
            .filter(Objects::nonNull)
            .count();
        LOGGER.debug("Loaded {} objects from {}", count, clazz.getName());
    }
}
