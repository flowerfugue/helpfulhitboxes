package com.github.abigailfails.helpfulhitboxes.fabriclike;

import com.github.abigailfails.helpfulhitboxes.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class ModConfigImpl {
    /**
     * This is our actual method to {@link ModConfig#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
