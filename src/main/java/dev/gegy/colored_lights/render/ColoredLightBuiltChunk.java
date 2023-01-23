package dev.gegy.colored_lights.render;

import org.jetbrains.annotations.Nullable;

import dev.gegy.colored_lights.ColoredLightCorner;
import dev.gegy.colored_lights.chunk.ColoredLightChunkSection;

public interface ColoredLightBuiltChunk {
    void updateChunkLight(int generation, @Nullable ColoredLightCorner[] corners);
    
    long getPackedChunkLightColors();
    
    @Nullable
    ColoredLightCorner[] getChunkLightColors();
    
    int getChunkLightGeneration();
    
    default boolean isLightOutdated(ColoredLightChunkSection section) {
        return this.getChunkLightGeneration() != section.getColoredLightGeneration();
    }
}
