package com.github.abigailfails.helpfulhitboxes.mixin;

import com.github.abigailfails.helpfulhitboxes.ModOptions;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/SimpleOptionsSubScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/client/Options;Lnet/minecraft/network/chat/Component;[Lnet/minecraft/client/OptionInstance;)V"))
    private static void addNewOption(Args args) {
        Object arg1 = args.get(1);
        Object arg2 = args.get(3);
        if (arg1 instanceof Options options && arg2 instanceof OptionInstance<?>[] optionInstances) {
            OptionInstance<?>[] newOptionInstances = new OptionInstance[optionInstances.length+1];
            int insertOrdinal = optionInstances.length-1;
            for (int i=0; i< optionInstances.length; i++)
                if (options.toggleSprint().equals(optionInstances[i]))
                    insertOrdinal = i;
            System.arraycopy(optionInstances, 0, newOptionInstances, 0, insertOrdinal+1);
            newOptionInstances[insertOrdinal+1] = ModOptions.TOGGLE_HITBOXES;
            System.arraycopy(optionInstances, insertOrdinal+1, newOptionInstances, insertOrdinal+2,newOptionInstances.length-(insertOrdinal+2));
            args.set(3, newOptionInstances);
        }
    }
}
