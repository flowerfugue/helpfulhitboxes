package com.github.abigailfails.helpfulhitboxes.forge;

import com.github.abigailfails.helpfulhitboxes.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ModConfigImpl {
    /**
     * This is our actual method to {@link ModConfig#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
