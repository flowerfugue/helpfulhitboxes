package com.github.abigailfails.helpfulhitboxes.fabric;

import com.github.abigailfails.helpfulhitboxes.HelpfulHitboxes;
import com.github.abigailfails.helpfulhitboxes.ModConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class HelpfulHitboxesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HelpfulHitboxes.init();
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleResourceReloadListener<JsonArray>() {
            @Override
            public ResourceLocation getFabricId() {
                return null;
            }

            @Override
            public CompletableFuture<JsonArray> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.supplyAsync(() -> ModConfig.readConfig(manager, profiler), executor);
            }

            @Override
            public CompletableFuture<Void> apply(JsonArray data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.runAsync(() -> ModConfig.applyConfig(data, manager, profiler), executor);
            }
        });
    }
}
