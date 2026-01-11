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

import dev.tophatcat.projecticbp.entities.BallisticPenguinEntity;
import dev.tophatcat.projecticbp.registry.BallisticEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = ProjectICBP.MOD_ID)
@Mod(ProjectICBP.MOD_ID)
public class ProjectICBPNeoForge {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE, ProjectICBP.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProjectICBP.MOD_ID);
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ProjectICBP.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectICBP.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
        Registries.SOUND_EVENT, ProjectICBP.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB, ProjectICBP.MOD_ID);
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(
        Registries.MEMORY_MODULE_TYPE, ProjectICBP.MOD_ID);

    public ProjectICBPNeoForge(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        BLOCKS.register(bus);
        ENTITIES.register(bus);
        ITEMS.register(bus);
        SOUND_EVENTS.register(bus);
        CREATIVE_TABS.register(bus);
        MEMORY_MODULE_TYPES.register(bus);
        ProjectICBP.init();
    }

    @SubscribeEvent
    private static void registerEntityAttributes(final EntityAttributeCreationEvent event) {
        BallisticEntities.registerEntityAttributes(event::put);
    }

    @SubscribeEvent
    private static void registerSpawnPlacements(final RegisterSpawnPlacementsEvent event) {
        event.register(BallisticEntities.BALLISTIC_PENGUIN.get(),
            SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            BallisticPenguinEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
