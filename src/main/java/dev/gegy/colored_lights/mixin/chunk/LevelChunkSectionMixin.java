package dev.gegy.colored_lights.mixin.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import dev.gegy.colored_lights.ColoredLightValue;
import dev.gegy.colored_lights.chunk.ChunkColoredLightSampler;
import dev.gegy.colored_lights.chunk.ColoredLightChunkSection;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;

@Mixin(LevelChunkSection.class)
public abstract class LevelChunkSectionMixin implements ColoredLightChunkSection {
    @Shadow
    public abstract boolean hasOnlyAir();
    
    @Unique
    private ColoredLightValue[] coloredLightPoints;
    @Unique
    private int coloredLightGeneration;
    
    @Inject(method = "setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFluidState()Lnet/minecraft/world/level/material/FluidState;", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD, require = 1)
    private void setBlockState(int x, int y, int z, BlockState state, boolean lock, CallbackInfoReturnable<BlockState> ci, BlockState lastState) {
        if (lastState.getLightEmission() != 0 || state.getLightEmission() != 0)
            this.invalidateColoredLight();
    }
    
    @Inject(method = "recalcBlockCounts", at = @At("HEAD"), require = 1)
    private void recalcBlockCounts(CallbackInfo ci) {
        this.invalidateColoredLight();
    }
    
    private void invalidateColoredLight() {
        this.coloredLightPoints = null;
        this.coloredLightGeneration++;
    }
    
    @Override
    public ColoredLightValue getColoredLightPoint(LevelAccessor world, SectionPos sectionPos, int x, int y, int z) {
        if (this.hasOnlyAir())
            return ColoredLightValue.NO;
        
        var points = this.coloredLightPoints;
        if (points == null)
            this.coloredLightPoints = points = ChunkColoredLightSampler.sampleCorners(world, sectionPos, (LevelChunkSection) (Object) this);
        
        return points[ChunkColoredLightSampler.octantIndex(x, y, z)];
    }
    
    @Override
    public int getColoredLightGeneration() {
        return this.coloredLightGeneration;
    }
}
