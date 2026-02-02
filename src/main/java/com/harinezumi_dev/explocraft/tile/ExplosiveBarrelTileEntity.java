package com.harinezumi_dev.explocraft.tile;


import com.harinezumi_dev.explocraft.registry.ModTileEntities;
import com.harinezumi_dev.explocraft.util.ExplosionHelper;

import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExplosiveBarrelTileEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);;
    private boolean activated = false;
    private int ticksRemaining = -1;

    @Override
    public void tick() {
        if (!level.isClientSide && activated) {
            ticksRemaining--;
            if (ticksRemaining <= 0) {
                explode();
            }
        }
    }

    public void startCountdown() {
        int redstoneCount = countRedstone();

        if (redstoneCount == 0) ticksRemaining = 5 * 20;
        else if (redstoneCount <= 3) ticksRemaining = 30 * 20;
        else if (redstoneCount <= 8) ticksRemaining = 60 * 20;
        else if (redstoneCount <= 12) ticksRemaining = 90 * 20;
        else ticksRemaining = 120 * 20;

        activated = true;
        setChanged();
    }

    public float getExplosionPower() {
        int gunpowder = Math.min(countGunpowder(), 20);
        return 2.0F + (gunpowder / 2.0F);
    }

    private void explode() {
        ExplosionHelper.explode(level, worldPosition, getExplosionPower());
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.explocraft.explosive_barrel");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null; // tuta gui later
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

}
