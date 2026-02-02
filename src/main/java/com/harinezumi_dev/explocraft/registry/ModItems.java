package com.harinezumi_dev.explocraft.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "explocraft");

    public static final RegistryObject<Item> EXPLOSIVE_BARREL = ITEMS.register("explosive_barrel",
        () -> new BlockItem(ModBlocks.EXPLOSIVE_BARREL.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
}
