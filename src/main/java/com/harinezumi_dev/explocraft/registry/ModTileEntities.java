package com.harinezumi_dev.explocraft.registry;

import com.harinezumi_dev.explocraft.tile.ExplosiveBarrelTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
        DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "explocraft");

    public static final RegistryObject<TileEntityType<ExplosiveBarrelTileEntity>> EXPLOSIVE_BARREL =
        TILE_ENTITIES.register("explosive_barrel",
            () -> TileEntityType.Builder.of(ExplosiveBarrelTileEntity::new,
                ModBlocks.EXPLOSIVE_BARREL.get()).build(null));
}
