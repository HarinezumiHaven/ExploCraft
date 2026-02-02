package com.harinezumi_dev.explocraft.registry;

import com.harinezumi_dev.explocraft.block.ExplosiveBarrelBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "explocraft");

    public static final RegistryObject<Block> EXPLOSIVE_BARREL = BLOCKS.register("explosive_barrel",
        () -> new ExplosiveBarrelBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.5F)));
}
