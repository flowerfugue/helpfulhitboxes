package com.github.abigailfails.helpfulhitboxes.fabric;

import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class ModConfigImpl {
    /**
     * This is our actual method to {@link com.github.abigailfails.helpfulhitboxes.ModConfig#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
