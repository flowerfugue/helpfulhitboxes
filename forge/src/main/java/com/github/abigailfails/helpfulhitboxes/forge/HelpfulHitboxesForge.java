package com.github.abigailfails.helpfulhitboxes.forge;

import com.github.abigailfails.helpfulhitboxes.ModConfig;
import com.github.abigailfails.helpfulhitboxes.HelpfulHitboxes;
import com.github.abigailfails.helpfulhitboxes.ModOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HelpfulHitboxes.MOD_ID)
public class HelpfulHitboxesForge {

    public HelpfulHitboxesForge() {
        HelpfulHitboxes.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addClientReloadListeners);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeyMaps);
        MinecraftForge.EVENT_BUS.addListener(this::onTagsUpdated);
    }

    private void addClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SimplePreparableReloadListener<JsonObject>() {
            @Override
            protected JsonObject prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                return ModConfig.readConfig();
            }

            @Override
            protected void apply(JsonObject jsonObject, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                ModConfig.applyConfig(jsonObject);
            }
        });
    }

    public void registerKeyMaps(RegisterKeyMappingsEvent event) {
        event.register(ModOptions.DISABLE_BEHAVIOUR);
    }

    private void onTagsUpdated(TagsUpdatedEvent event) {
        if (event.getUpdateCause().equals(TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED) && HelpfulHitboxes.COMPATIBLE_BLOCKS != null)
            HelpfulHitboxes.COMPATIBLE_BLOCKS.updateTags();
    }
}
