package dev.gegy.colored_lights.provider;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.WorldView;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockLightColorProvider {
    @Nullable
    Vec3f get(WorldView world, BlockPos pos, BlockState state);
}
