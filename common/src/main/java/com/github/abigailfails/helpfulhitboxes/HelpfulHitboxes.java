package com.github.abigailfails.helpfulhitboxes;

import java.util.HashSet;

//TODO implement keybind reader as suggested in the github issue
public class HelpfulHitboxes {
    public static final String MOD_ID = "helpfulhitboxes";
    public static boolean ALL_BLOCKS_COMPATIBLE = false;
    public static CompatibleBlockList COMPATIBLE_BLOCKS;

    public record CompatibleBlockList(HashSet<String> ungroupedBlocks, HashSet<HashSet<String>> blockGroups) {
        public boolean isCompatible(String targetedBlockID, String heldBlockID) {
            boolean useFullShape = false;
            if (targetedBlockID.equals(heldBlockID) && HelpfulHitboxes.COMPATIBLE_BLOCKS.ungroupedBlocks().contains(targetedBlockID))
                useFullShape = true;
            else if (HelpfulHitboxes.COMPATIBLE_BLOCKS.blockGroups().stream().anyMatch(g -> g.contains(targetedBlockID) && g.contains(heldBlockID)))
                useFullShape = true;
            return useFullShape;
        }

        public void updateTags() {
            ModConfig.updateTagsInBlockList(this.ungroupedBlocks());
            this.blockGroups().forEach(ModConfig::updateTagsInBlockList);
        }
    }

    public static void init() {

    }

}
