package com.harinezumi_dev.explocraft.tile;

import com.harinezumi_dev.explocraft.container.ExplosiveBarrelContainer;
import com.harinezumi_dev.explocraft.registry.ModTileEntities;
import com.harinezumi_dev.explocraft.util.ExplosionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExplosiveBarrelTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private boolean activated = false;
    private int ticksRemaining = -1;

    public ExplosiveBarrelTileEntity() {
        super(ModTileEntities.EXPLOSIVE_BARREL.get());
    }

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
        if (activated) return;

        int redstoneCount = countRedstone();

        if (redstoneCount == 0) ticksRemaining = 5 * 20;
        else if (redstoneCount <= 3) ticksRemaining = 30 * 20;
        else if (redstoneCount <= 8) ticksRemaining = 60 * 20;
        else if (redstoneCount <= 12) ticksRemaining = 90 * 20;
        else ticksRemaining = 120 * 20;

        activated = true;
        setChanged();
    }

    public boolean isActivated() {
        return activated;
    }

    private int countRedstone() {
        int count = 0;
        for (ItemStack stack : items) {
            if (stack.getItem() == Items.REDSTONE) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private int countGunpowder() {
        int count = 0;
        for (ItemStack stack : items) {
            if (stack.getItem() == Items.GUNPOWDER) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public float getExplosionPower() {
        int gunpowder = Math.min(countGunpowder(), 20);
        return Math.min(2.0F + (gunpowder / 2.0F), 10.0F);
    }

    public void explode() {
        ExplosionHelper.explode(level, worldPosition, getExplosionPower());
        level.removeBlock(worldPosition, false);
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
        return new ExplosiveBarrelContainer(id, player, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.putBoolean("Activated", activated);
        compound.putInt("TicksRemaining", ticksRemaining);
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        activated = compound.getBoolean("Activated");
        ticksRemaining = compound.getInt("TicksRemaining");
    }
}
