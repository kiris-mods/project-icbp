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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class BallisticItemRegistry {

    public static void init() {
    }

    public static final Supplier<SpawnEggItem> BALLISTIC_PENGUIN_SPAWN_EGG
        = ProjectICBPCommon.COMMON_PLATFORM.registerItem("ballistic_penguin_spawn_egg",
        ProjectICBPCommon.COMMON_PLATFORM.makeSpawnEgg(BallisticEntityRegistry.BALLISTIC_PENGUIN,
            0x191919, 0xf3f3f3, new Item.Properties()));
}
