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
package dev.tophatcat.projecticbp.platform;

import com.google.auto.service.AutoService;
import dev.tophatcat.projecticbp.ProjectICBP;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
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

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@AutoService(IPlatform.class)
public class PlatformFabric implements IPlatform {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(
        String id, Supplier<BlockEntityType<T>> blockEntityType) {
        return registerSupplier(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, blockEntityType);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(id, factory, BlockBehaviour.Properties::of);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> propertiesGetter) {
        return registerWithProperties(BuiltInRegistries.BLOCK, id, factory, propertiesGetter, (resourceKey, props) -> props.setId(resourceKey));
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> entity, MobCategory mobCategory, UnaryOperator<EntityType.Builder<T>> properties) {
        return registerSupplier(BuiltInRegistries.ENTITY_TYPE, id, () -> properties.apply(EntityType.Builder.of(entity, mobCategory)).build(ResourceKey.create(Registries.ENTITY_TYPE, ProjectICBP.id(id))));
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Function<Item.Properties, T> factory) {
        return registerItem(id, factory, Item.Properties::new);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Function<Item.Properties, T> factory, Supplier<Item.Properties> propertiesGetter) {
        return registerWithProperties(BuiltInRegistries.ITEM, id, factory, propertiesGetter, (resourceKey, props) -> props.setId(resourceKey));
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound) {
        return registerSupplier(BuiltInRegistries.SOUND_EVENT, id, sound);
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeModeTab(String id, Supplier<ItemStack> icon, Consumer<CreativeModeTab.Builder> tab) {
        return registerSupplier(BuiltInRegistries.CREATIVE_MODE_TAB, id, () -> {
            var builder = FabricItemGroup.builder().icon(icon).title(Component.translatable(Util.makeDescriptionId("itemGroup", ProjectICBP.id(id))));
            tab.accept(builder);
            return builder.build();
        });
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<E>> entityType, Supplier<Item.Properties> properties) {
        return registerItem(id, SpawnEggItem::new, () -> properties.get().spawnEgg(entityType.get()));
    }

    @Override
    public <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String name, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return registerSupplier(BuiltInRegistries.MEMORY_MODULE_TYPE, name, memoryModuleType);
    }

    static <T> Supplier<T> registerSupplier(Registry<? super T> registry, String id, Supplier<T> factory) {
        var registeredObject = Registry.register(registry, ProjectICBP.id(id), factory.get());
        return () -> registeredObject;
    }

    static <R, T extends R, P> Supplier<T> registerWithProperties(Registry<R> registry, String id, Function<P, T> factory, Supplier<P> propertiesGetter, BiFunction<ResourceKey<R>, P, P> keySetter) {
        var registryId = ResourceKey.create(registry.key(), ProjectICBP.id(id));
        var registeredObject = Registry.register(registry, registryId, factory.apply(keySetter.apply(registryId, propertiesGetter.get())));
        return () -> registeredObject;
    }

    static <T, R extends Registry<? super T>> Holder<T> registerHolder(Registry<? super T> registry, String id, Supplier<T> factory) {
        return Registry.registerForHolder(registry, ProjectICBP.id(id), factory.get());
    }
}
