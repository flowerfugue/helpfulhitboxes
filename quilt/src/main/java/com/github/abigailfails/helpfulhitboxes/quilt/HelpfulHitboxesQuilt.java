package com.github.abigailfails.helpfulhitboxes.quilt;

import com.github.abigailfails.helpfulhitboxes.fabriclike.HelpfulHitboxesFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class HelpfulHitboxesQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        HelpfulHitboxesFabricLike.init();
    }
}
