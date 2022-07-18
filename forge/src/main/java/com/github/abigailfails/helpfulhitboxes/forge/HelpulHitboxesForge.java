package com.github.abigailfails.helpfulhitboxes.forge;

import com.github.abigailfails.helpfulhitboxes.ModConfig;
import com.github.abigailfails.helpfulhitboxes.HelpfulHitboxes;
import com.google.gson.JsonArray;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HelpfulHitboxes.MOD_ID)
public class HelpulHitboxesForge {

    public HelpulHitboxesForge() {
        HelpfulHitboxes.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addClientReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoad);
    }

    private void addClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SimplePreparableReloadListener<JsonArray>() {
            @Override
            protected JsonArray prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                return ModConfig.readConfig(resourceManager, profilerFiller);
            }

            @Override
            protected void apply(JsonArray jsonArray, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                ModConfig.applyConfig(jsonArray, resourceManager, profilerFiller);
            }
        });
    }

    private void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel().isClientSide())
            ModConfig.fillAllTagsInCompatibleBlocks();
    }
}
