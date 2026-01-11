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

import dev.tophatcat.projecticbp.platform.IPlatform;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.Supplier;

public class BallisticMemoryTypes {
    public static void init() {

    }

    public static final Supplier<MemoryModuleType<Unit>> CALMED = registerMemoryType("calmed", () -> new MemoryModuleType<>(Optional.of(Unit.CODEC)));
    public static final Supplier<MemoryModuleType<Unit>> EATEN_FISH = registerMemoryType("eaten_fish", () -> new MemoryModuleType<>(Optional.of(Unit.CODEC)));

    private static <T> Supplier<MemoryModuleType<T>> registerMemoryType(String name, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return IPlatform.INSTANCE.registerMemoryModuleType(name, memoryModuleType);
    }

}
