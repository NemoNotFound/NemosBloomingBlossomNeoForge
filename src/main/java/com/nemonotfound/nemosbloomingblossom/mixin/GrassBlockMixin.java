package com.nemonotfound.nemosbloomingblossom.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.GrassBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {

    @ModifyExpressionValue(
            method = "performBonemeal",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I", ordinal = 5)
    )
    private int nextInt(int original, @Local(ordinal = 1) BlockPos blockPos, @Local(argsOnly = true) ServerLevel world) {
        if (world.getBiome(blockPos).is(Biomes.CHERRY_GROVE)) {
            return original > 2 ? 0 : original;
        } else {
            return original;
        }
    }
}
