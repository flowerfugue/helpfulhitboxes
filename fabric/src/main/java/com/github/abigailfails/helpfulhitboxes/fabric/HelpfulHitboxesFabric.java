package com.github.abigailfails.helpfulhitboxes.fabric;

import com.github.abigailfails.helpfulhitboxes.fabriclike.HelpfulHitboxesFabricLike;
import net.fabricmc.api.ModInitializer;

public class HelpfulHitboxesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HelpfulHitboxesFabricLike.init();
    }
}
