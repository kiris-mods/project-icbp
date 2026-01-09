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

import dev.tophatcat.projecticbp.ProjectICBP;
import dev.tophatcat.projecticbp.ProjectICBPNeoForge;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializer;
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
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class PlatformNeoForge implements IPlatform {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        var modList = ModList.get();
        if (modList == null) {
            return FMLLoader.getCurrent().getLoadingModList().getMods().stream().anyMatch(it -> it.getModId().equals(modId));
        }

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.isProduction();
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> blockEntityType) {
        return ProjectICBPNeoForge.BLOCK_ENTITIES.register(id, blockEntityType);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> factory) {
        return ProjectICBPNeoForge.BLOCKS.registerBlock(id, factory);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> propertiesGetter) {
        return ProjectICBPNeoForge.BLOCKS.registerBlock(id, factory, propertiesGetter);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> entity, MobCategory mobCategory, UnaryOperator<EntityType.Builder<T>> properties) {
        return ProjectICBPNeoForge.ENTITIES.registerEntityType(id, entity, mobCategory, properties);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Function<Item.Properties, T> factory) {
        return ProjectICBPNeoForge.ITEMS.registerItem(id, factory);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Function<Item.Properties, T> factory, Supplier<Item.Properties> propertiesGetter) {
        return ProjectICBPNeoForge.ITEMS.registerItem(id, factory, propertiesGetter);
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound) {
        return ProjectICBPNeoForge.SOUND_EVENTS.register(id, sound);
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeModeTab(String id, Supplier<ItemStack> icon, Consumer<CreativeModeTab.Builder> tab) {
        return ProjectICBPNeoForge.CREATIVE_TABS.register(id, () -> {
            var builder = CreativeModeTab.builder().icon(icon).title(Component.translatable(Util.makeDescriptionId("itemGroup", ProjectICBP.id(id))));
            tab.accept(builder);
            return builder.build();
        });
    }

    @Override
    public <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String name, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return ProjectICBPNeoForge.MEMORY_MODULE_TYPES.register(name, memoryModuleType);
    }
}
