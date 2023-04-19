package com.github.abigailfails.helpfulhitboxes;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class ModOptions {
    public static final Component ENABLED_MESSAGE = Component.translatable("message.helpfulhitboxes.behaviour_enabled");
    public static final Component DISABLED_MESSAGE = Component.translatable("message.helpfulhitboxes.behaviour_disabled");
    private static final Component MOVEMENT_TOGGLE = Component.translatable("options.key.toggle");
    private static final Component MOVEMENT_HOLD = Component.translatable("options.key.hold");

    public static final OptionInstance<Boolean> TOGGLE_HITBOXES = new OptionInstance<>(
            "options.helpfulhitboxes.toggle_hitboxes",
            OptionInstance.cachedConstantTooltip(Component.translatable("options.helpfulhitboxes.toggle_hitboxes.tooltip")),
            (component, bool) -> bool ? MOVEMENT_TOGGLE : MOVEMENT_HOLD,
            OptionInstance.BOOLEAN_VALUES,
            true, //TODO this changes if the config option is on
            bool -> {}
            );
    public static final ToggleKeyMapping DISABLE_BEHAVIOUR = new ToggleKeyMapping(
            "key.helpfulhitboxes.context_specific_hitboxes",
            InputConstants.KEY_Y,
            "key.helpfulhitboxes.categories.helpfulhitboxes",
            TOGGLE_HITBOXES::get
    ) {
        @Override
        public void setDown(boolean bl) {
            super.setDown(bl);
            if (this.needsToggle.getAsBoolean() && bl) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null)
                    player.displayClientMessage(!this.isDown() ? ENABLED_MESSAGE : DISABLED_MESSAGE, true);
            }
        }
    };
}
