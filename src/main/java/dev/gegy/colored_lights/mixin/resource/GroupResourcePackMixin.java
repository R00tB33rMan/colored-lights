package dev.gegy.colored_lights.mixin.resource;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.gegy.colored_lights.resource.ResourceExtention;
import net.fabricmc.fabric.impl.resource.loader.GroupResourcePack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;

@Mixin(GroupResourcePack.class)
public class GroupResourcePackMixin {
    
    @Inject(method = "appendResources", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = Shift.AFTER), require = 1)
    public void afterAdded(PackType type, ResourceLocation id, List<Resource> resources, CallbackInfo info) {
        ((ResourceExtention) resources.get(resources.size() - 1)).setLocation(id);
    }
    
}
