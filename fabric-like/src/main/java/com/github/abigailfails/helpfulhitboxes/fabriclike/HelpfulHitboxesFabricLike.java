package com.github.abigailfails.helpfulhitboxes.fabriclike;

import com.github.abigailfails.helpfulhitboxes.HelpfulHitboxes;
import com.github.abigailfails.helpfulhitboxes.ModConfig;
import com.github.abigailfails.helpfulhitboxes.ModOptions;
import com.google.gson.JsonArray;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class HelpfulHitboxesFabricLike {
    public static ResourceLocation FABRIC_ID = new ResourceLocation("helpfulhitboxes", "config_reload");

    public static void init() {
        HelpfulHitboxes.init();
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleResourceReloadListener<JsonArray>() {
            @Override
            public ResourceLocation getFabricId() {
                return FABRIC_ID;
            }

            @Override
            public CompletableFuture<JsonArray> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.supplyAsync(ModConfig::readConfig, executor);
            }

            @Override
            public CompletableFuture<Void> apply(JsonArray data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.runAsync(() -> ModConfig.applyConfig(data), executor);
            }
        });
        KeyBindingHelper.registerKeyBinding(ModOptions.DISABLE_BEHAVIOUR);
    }
}
