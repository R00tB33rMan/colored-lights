package dev.gegy.colored_lights.mixin.resource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.fabricmc.fabric.impl.resource.loader.GroupResourcePack;

@Mixin(GroupResourcePack.class)
public class GroupResourcePackMixin {
    
    @Inject(method = "appendResources", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = Shift.AFTER), require = 1,
            locals = LocalCapture.PRINT)
    public void afterAdded(CallbackInfo info) {
        // TODO
    }
    
}
