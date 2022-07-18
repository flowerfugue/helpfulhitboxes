package com.github.abigailfails.helpfulhitboxes;

import java.util.HashSet;

public class HelpfulHitboxes {
    public static final String MOD_ID = "helpfulhitboxes";
    public static CompatibleBlockList COMPATIBLE_BLOCKS;

    public record CompatibleBlockList(HashSet<String> ungroupedBlocks, HashSet<HashSet<String>> blockGroups) {}

    public static void init() {
    }
}
