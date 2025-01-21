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
package dev.tophatcat.projecticbp.registry;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BallisticEntityRegistry {

    public static void init() {
    }

    public static final Supplier<EntityType<BallisticPenguin>> BALLISTIC_PENGUIN = registerEntity(
        "ballistic_penguin", BallisticPenguin::new, 0.8F, 1.5F, 1.4F);

    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> register) {
        register.accept(BALLISTIC_PENGUIN.get(), BallisticPenguin.createAttributes().build());
    }

    private static <T extends Mob> Supplier<EntityType<T>> registerEntity(
        String name, EntityType.EntityFactory<T> entity, float width, float height, float eyeHeight) {
        return ProjectICBPCommon.COMMON_PLATFORM.registerEntity(
            name, () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).eyeHeight(eyeHeight).build(name));
    }
}
