package com.github.abigailfails.helpfulhitboxes.fabric.mixin.fabric;

import com.github.abigailfails.helpfulhitboxes.HelpfulHitboxes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
    @Inject(at = @At("TAIL"), method = "updateRegistryTags(Lnet/minecraft/core/RegistryAccess;)V")
    private void updateBlockListTags(RegistryAccess registryAccess, CallbackInfo ci) {
        if (HelpfulHitboxes.COMPATIBLE_BLOCKS != null)
            HelpfulHitboxes.COMPATIBLE_BLOCKS.updateTags();
    }
}
