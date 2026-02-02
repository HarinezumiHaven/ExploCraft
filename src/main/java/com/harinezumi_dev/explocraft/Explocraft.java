package com.harinezumi_dev.explocraft;

import com.harinezumi_dev.explocraft.client.ScreenExplosiveBarrel;
import com.harinezumi_dev.explocraft.registry.ModBlocks;
import com.harinezumi_dev.explocraft.registry.ModContainers;
import com.harinezumi_dev.explocraft.registry.ModItems;
import com.harinezumi_dev.explocraft.registry.ModTileEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("explocraft")
public class Explocraft {
    private static final Logger LOGGER = LogManager.getLogger();

    public Explocraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("ExploCraft setup complete");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenManager.register(ModContainers.EXPLOSIVE_BARREL.get(), ScreenExplosiveBarrel::new);
        });
    }
}
