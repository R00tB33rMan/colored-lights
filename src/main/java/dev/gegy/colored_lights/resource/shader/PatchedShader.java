package dev.gegy.colored_lights.resource.shader;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.gl.GlUniform;

public interface PatchedShader {
    @Nullable
    GlUniform getPatchedUniform(PatchedUniform uniform);
}
