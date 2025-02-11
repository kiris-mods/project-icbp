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

import dev.tophatcat.projecticbp.client.BallisticRenderingNeo;
import dev.tophatcat.projecticbp.entities.BallisticPenguin;
import dev.tophatcat.projecticbp.registry.BallisticEntityRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ProjectICBPCommon.MOD_ID)
public class ProjectICBPNeo {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
        Registries.SOUND_EVENT, ProjectICBPCommon.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        Registries.BLOCK, ProjectICBPCommon.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE, ProjectICBPCommon.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
        Registries.ENTITY_TYPE, ProjectICBPCommon.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB, ProjectICBPCommon.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        Registries.ITEM, ProjectICBPCommon.MOD_ID);
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(
        Registries.MEMORY_MODULE_TYPE, ProjectICBPCommon.MOD_ID);

    public ProjectICBPNeo(IEventBus bus) {
        SOUND_EVENTS.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
        ENTITIES.register(bus);
        CREATIVE_TABS.register(bus);
        ITEMS.register(bus);
        MEMORY_MODULE_TYPES.register(bus);
        bus.<EntityAttributeCreationEvent>addListener(event
            -> BallisticEntityRegistry.registerEntityAttributes(event::put));
        ProjectICBPCommon.init();
        bus.addListener(this::registerSpawnPlacements);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(BallisticRenderingNeo::registerEntityModels);
        }
    }

    private void registerSpawnPlacements(final RegisterSpawnPlacementsEvent event) {
        event.register(BallisticEntityRegistry.BALLISTIC_PENGUIN.get(), SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BallisticPenguin::checkSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.AND);
    }
}
