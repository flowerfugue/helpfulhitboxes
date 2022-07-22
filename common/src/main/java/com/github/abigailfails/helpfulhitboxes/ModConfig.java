package com.github.abigailfails.helpfulhitboxes;

import com.google.gson.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.logging.ILogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModConfig {
    public static final String CONFIG_FILE = getConfigDirectory().toString() + "/helpfulhitboxes.json";

    /**
     * This is the example method for demonstrating <code>@ExpectPlatform</code> but i'm keeping it because it's actually useful!
     */
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    public static void updateTagsInBlockList(HashSet<String> blockList) {
        List<String> toAdd = new ArrayList<>();
        List<String> toRemove = new ArrayList<>();
        for (String string : blockList) {
            if (addTagElementsOrCache(string, toAdd))
                toRemove.add(string);
        }
        toRemove.forEach(blockList::remove);
        blockList.addAll(toAdd);
    }

    /**
     * Reads a tag ID and adds all its elements to a specified {@code HashSet}. If the tag is empty, it adds the tag
     * ID to the set instead.
     *
     * @return If true, elements from the tag were added to the set. If false, the cache placeholder was added instead
     * */
    private static boolean addTagElementsOrCache(String tag, Collection<String> toAdd) {
        HashSet<String> tagElements = new HashSet<>();
        Registry.BLOCK.getTagOrEmpty(TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(tag.substring(1)))).forEach(h -> {
            tagElements.add(h.value().getDescriptionId());
        });
        if (!tagElements.isEmpty()) {
            toAdd.addAll(tagElements);
            return true;
        } else toAdd.add(tag);
        return false;
    }

    public static JsonObject readConfig() {
        JsonObject configJson;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (!new File(CONFIG_FILE).exists()) {
                configJson = defaultConfigJson();
                FileWriter writer = new FileWriter(CONFIG_FILE);
                gson.toJson(configJson, writer);
                writer.close();
            } else {
                Reader reader = Files.newBufferedReader(Paths.get(CONFIG_FILE));
                configJson = gson.fromJson(reader, JsonObject.class);
                if (!Optional.ofNullable(configJson.get("all_blocks_use_full_hitboxes")).orElse(new JsonPrimitive("")).getAsBoolean()) {
                    configJson.addProperty("all_blocks_use_full_hitboxes", false);
                    FileWriter writer = new FileWriter(CONFIG_FILE);
                    gson.toJson(configJson, writer);
                    writer.close();
                }
                reader.close();
            }
        } catch (IOException | JsonParseException e) {
            Logger.getGlobal().log(Level.SEVERE, "Cannot access HelpfulHitboxes config file or it is invalid, reverting to default value...");
            return defaultConfigJson();
        }
        return configJson;
    }

    public static void applyConfig(JsonObject json) {
        HelpfulHitboxes.ALL_BLOCKS_COMPATIBLE = Optional.ofNullable(json.get("all_blocks_use_full_hitboxes")).orElse(new JsonPrimitive("")).getAsBoolean();
        if (!HelpfulHitboxes.ALL_BLOCKS_COMPATIBLE) try {
            HashSet<String> ungrouped = new HashSet<>();
            HashSet<HashSet<String>> blockGroups = new HashSet<>();
            readBlocklistFromJson(json.getAsJsonArray("compatible_blocks"), ungrouped, blockGroups);
            HelpfulHitboxes.COMPATIBLE_BLOCKS = new HelpfulHitboxes.CompatibleBlockList(ungrouped, blockGroups);
        } catch (JsonParseException e) {
            HashSet<String> ungrouped = new HashSet<>();
            HashSet<HashSet<String>> blockGroups = new HashSet<>();
            readBlocklistFromJson(defaultConfigJson().getAsJsonArray("compatible_blocks"), ungrouped, blockGroups);
        } else HelpfulHitboxes.COMPATIBLE_BLOCKS = null;
    }

    private static JsonObject defaultConfigJson() {
        JsonArray compatibleBlocks = new JsonArray();
        compatibleBlocks.add("minecraft:chain");
        compatibleBlocks.add("minecraft:end_rod");
        compatibleBlocks.add("minecraft:scaffolding");

        JsonArray fences = new JsonArray();
        fences.add("#minecraft:fences");
        JsonObject fencesObject = new JsonObject();
        fencesObject.add("group", fences);
        compatibleBlocks.add(fencesObject);

        JsonArray panes = new JsonArray();
        //Adding the loader tags in case mods add new panes
        panes.add("#forge:glass_panes");
        panes.add("#c:glass_panes");
        panes.add("minecraft:glass_pane");
        panes.add("minecraft:gray_stained_glass_pane");
        panes.add("minecraft:black_stained_glass_pane");
        panes.add("minecraft:orange_stained_glass_pane");
        panes.add("minecraft:blue_stained_glass_pane");
        panes.add("minecraft:brown_stained_glass_pane");
        panes.add("minecraft:cyan_stained_glass_pane");
        panes.add("minecraft:green_stained_glass_pane");
        panes.add("minecraft:light_blue_stained_glass_pane");
        panes.add("minecraft:light_gray_stained_glass_pane");
        panes.add("minecraft:lime_stained_glass_pane");
        panes.add("minecraft:magenta_stained_glass_pane");
        panes.add("minecraft:pink_stained_glass_pane");
        panes.add("minecraft:purple_stained_glass_pane");
        panes.add("minecraft:red_stained_glass_pane");
        panes.add("minecraft:white_stained_glass_pane");
        panes.add("minecraft:yellow_stained_glass_pane");
        panes.add("minecraft:iron_bars");
        JsonObject panesObject = new JsonObject();
        panesObject.add("group", panes);
        compatibleBlocks.add(panesObject);

        JsonObject config = new JsonObject();
        config.addProperty("all_blocks_use_full_hitboxes", false);
        config.add("compatible_blocks", compatibleBlocks);
        return config;
    }

    private static void readBlocklistFromJson(JsonArray array, HashSet<String> ungrouped, HashSet<HashSet<String>> blockGroups) {
        for (JsonElement element : array) {
            if (element.isJsonObject()) {
                HashSet<String> blockGroup = new HashSet<>();
                readBlocklistFromJson(element.getAsJsonObject().getAsJsonArray("group"), blockGroup, blockGroups);
                blockGroups.add(blockGroup);
            } else {
                String string = element.getAsString();
                if (string.charAt(0) == '#')
                    addTagElementsOrCache(string, ungrouped);
                else ungrouped.add(Registry.BLOCK.get(new ResourceLocation(string)).getDescriptionId());
            }
        }
    }
}
