package com.harinezumi_dev.explocraft.block;

import com.harinezumi_dev.explocraft.registry.ModTileEntities;
import com.harinezumi_dev.explocraft.tile.ExplosiveBarrelTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ExplosiveBarrelBlock extends ContainerBlock {
    public ExplosiveBarrelBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return ModTileEntities.EXPLOSIVE_BARREL.get().create();
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isClientSide) {
            boolean hasSignal = world.hasNeighborSignal(pos);
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof ExplosiveBarrelTileEntity) {
                ExplosiveBarrelTileEntity barrel = (ExplosiveBarrelTileEntity) te;
                if (hasSignal && !barrel.isActivated()) {
                    barrel.startCountdown();
                }
            }
        }
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
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
                if (player instanceof ServerPlayerEntity) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, barrel, pos);
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }
}
