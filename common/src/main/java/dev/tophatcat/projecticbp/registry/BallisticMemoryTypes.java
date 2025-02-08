package dev.tophatcat.projecticbp.registry;

import dev.tophatcat.projecticbp.ProjectICBPCommon;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.Supplier;

public class BallisticMemoryTypes {
    public static void init() {

    }

    public static final Supplier<MemoryModuleType<Unit>> CALMED = registerMemoryType("calmed", ()-> new MemoryModuleType<>(Optional.of(Unit.CODEC)));

    private static <T> Supplier<MemoryModuleType<T>> registerMemoryType(String name, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return ProjectICBPCommon.COMMON_PLATFORM.registerMemoryModuleType(name, memoryModuleType);
    }

}
