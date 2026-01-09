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
package dev.tophatcat.projecticbp.platform;

import dev.tophatcat.projecticbp.ProjectICBP;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface IPlatform {

    IPlatform INSTANCE = ProjectICBP.loadService(IPlatform.class);

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default EnvironmentType getEnvironmentType() {
        return isDevelopmentEnvironment() ? EnvironmentType.DEVELOPMENT : EnvironmentType.PRODUCTION;
    }

    <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> blockEntityType);
    <T extends Block> Supplier<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> factory);
    <T extends Block> Supplier<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> propertiesGetter);
    <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> entity, MobCategory mobCategory, UnaryOperator<EntityType.Builder<T>> properties);
    <T extends Item> Supplier<T> registerItem(String id, Function<Item.Properties, T> factory);
    <T extends Item> Supplier<T> registerItem(String id, Function<Item.Properties, T> factory, Supplier<Item.Properties> propertiesGetter);
    <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound);
    Supplier<CreativeModeTab> registerCreativeModeTab(String id, Supplier<ItemStack> icon, Consumer<CreativeModeTab.Builder> tab);
    default <E extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<E>> entityType) {
        return registerSpawnEgg(id, entityType, Item.Properties::new);
    }
    default <E extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<E>> entityType, Supplier<Item.Properties> propertiesGetter) {
        return registerItem(id, SpawnEggItem::new, () -> propertiesGetter.get().spawnEgg(entityType.get()));
    }
    <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String name, Supplier<MemoryModuleType<T>> memoryModuleType);

    enum EnvironmentType {
        DEVELOPMENT("development"),
        PRODUCTION("production");

        private final String name;

        EnvironmentType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
