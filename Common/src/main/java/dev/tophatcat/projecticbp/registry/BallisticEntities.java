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
package dev.tophatcat.projecticbp.registry;

import dev.tophatcat.projecticbp.entities.BallisticPenguinEntity;
import dev.tophatcat.projecticbp.platform.IPlatform;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BallisticEntities {

    public static final Supplier<EntityType<BallisticPenguinEntity>> BALLISTIC_PENGUIN = IPlatform.INSTANCE.registerEntity(
        "ballistic_penguin", BallisticPenguinEntity::new, MobCategory.CREATURE, builder -> builder
            .sized(0.8F, 1.5F)
            .eyeHeight(1.4F)
            .fireImmune());

    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> register) {
        register.accept(BALLISTIC_PENGUIN.get(), BallisticPenguinEntity.createAttributes().build());
    }
}
