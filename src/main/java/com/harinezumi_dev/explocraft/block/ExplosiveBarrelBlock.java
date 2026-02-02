package com.harinezumi_dev.explocraft.items;

import net.minecraft.block.ContainerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class ExplosiveBarrelBlock extends ContainerBlock {
    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!world.isClientSide && world.hasNeighborSignal(pos)) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof ExplosiveBarrelTileEntity) {
                ((ExplosiveBarrelTileEntity) te).startCountdown();
            }
        }
    }

    // gambling part \(>o<)/
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof ExplosiveBarrelTileEntity) {
                ExplosiveBarrelTileEntity barrel = (ExplosiveBarrelTileEntity) te;
                if (barrel.isActivated() && world.random.nextFloat() < 0.5F) {
                    barrel.explode();
                    return ActionResultType.SUCCESS;
                }
            }
            return super.use(state, world, pos, player, hand, hit);
        }
    }


}
