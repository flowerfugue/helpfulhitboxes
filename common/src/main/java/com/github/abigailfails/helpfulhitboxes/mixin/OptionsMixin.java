package com.github.abigailfails.helpfulhitboxes.mixin;

import com.github.abigailfails.helpfulhitboxes.ModOptions;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsMixin {

   /* @Inject(at = @At("TAIL"), method = "<init>")
    private void addExtraOptions(Minecraft minecraft, File file, CallbackInfo ci) {
        ModOptions modOptions = new ModOptions();
        ModOptions.EXTRA_OPTIONS.put((Options) (Object) this, modOptions);
    }*/

    @Inject(at = @At("TAIL"), method = "processOptions")
    private void injectProcessOptions(Options.FieldAccess fieldAccess, CallbackInfo ci) {
        //ModOptions.TOGGLE_HITBOXES.set(fieldAccess.process("helpfulhitboxes_toggleHitboxes", ModOptions.TOGGLE_HITBOXES.get()));
        fieldAccess.process("helpfulhitboxes_toggleHitboxes", ModOptions.TOGGLE_HITBOXES);
    }
}
