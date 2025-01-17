package dev.gegy.colored_lights.mixin.resource;

import java.io.InputStream;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.gegy.colored_lights.resource.ResourceExtention;
import dev.gegy.colored_lights.resource.ResourcePatchManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;

@Mixin(Resource.class)
public class ResourceMixin implements ResourceExtention {
    
    @Unique
    public ResourceLocation id;
    @Shadow
    @Final
    @Mutable
    private IoSupplier<InputStream> streamSupplier;
    
    @Unique
    private boolean colored_lights$patchedResource;
    
    @Inject(method = "open", at = @At("HEAD"), require = 1)
    private void getInputStream(CallbackInfoReturnable<IoSupplier<InputStream>> ci) {
        if (!this.colored_lights$patchedResource) {
            this.colored_lights$patchedResource = true;
            this.streamSupplier = ResourcePatchManager.INSTANCE.patch(this.id, this.streamSupplier);
        }
    }
    
    @Override
    public void setLocation(ResourceLocation location) {
        this.id = location;
    }
}
