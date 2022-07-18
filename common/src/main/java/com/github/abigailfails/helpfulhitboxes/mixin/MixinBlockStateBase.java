package com.github.abigailfails.helpfulhitboxes.mixin;

import com.github.abigailfails.helpfulhitboxes.HelpfulHitboxes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class MixinBlockStateBase {
    @Shadow
    public Block getBlock() {
        return null;
    }

    @Inject(at = @At("HEAD"), method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true) //TODO should it really be getshape? this is only clientside but still, what if other entities use it
    private void modifyGetShape(BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext, CallbackInfoReturnable<VoxelShape> cir){
        if (HelpfulHitboxes.COMPATIBLE_BLOCKS != null && collisionContext instanceof EntityCollisionContext entityContext) {
            String targetName = this.getBlock().getDescriptionId();
            String heldName = entityContext.heldItem.getDescriptionId();
            boolean useFullShape = false;
            if (targetName.equals(heldName) && HelpfulHitboxes.COMPATIBLE_BLOCKS.ungroupedBlocks().contains(targetName))
                useFullShape = true;
            else if (HelpfulHitboxes.COMPATIBLE_BLOCKS.blockGroups().stream().anyMatch(g -> g.contains(targetName) && g.contains(heldName)))
                useFullShape = true;
            if (useFullShape)
                cir.setReturnValue(Shapes.block());
        }
    }
}
