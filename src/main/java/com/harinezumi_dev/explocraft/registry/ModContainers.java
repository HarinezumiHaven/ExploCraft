package com.harinezumi_dev.explocraft.registry;

import com.harinezumi_dev.explocraft.container.ExplosiveBarrelContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS =
        DeferredRegister.create(ForgeRegistries.CONTAINERS, "explocraft");

    public static final RegistryObject<ContainerType<ExplosiveBarrelContainer>> EXPLOSIVE_BARREL =
        CONTAINERS.register("explosive_barrel",
            () -> IForgeContainerType.create(ExplosiveBarrelContainer::new));
}
