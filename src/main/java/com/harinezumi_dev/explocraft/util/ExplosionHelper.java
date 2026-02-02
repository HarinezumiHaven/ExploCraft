package com.harinezumi_dev.explocraft.util;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;

public class ExplosionHelper {
    public static void explode(World world, BlockPos pos, float power) {
        world.explode(null,
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            power,
            Explosion.Mode.BREAK
        );
    }

}
