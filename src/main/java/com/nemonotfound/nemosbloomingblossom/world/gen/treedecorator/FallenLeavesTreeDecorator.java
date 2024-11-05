package com.nemonotfound.nemosbloomingblossom.world.gen.treedecorator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FallenLeavesTreeDecorator extends TreeDecorator {

    public static final FallenLeavesTreeDecorator INSTANCE = new FallenLeavesTreeDecorator();
    public static final MapCodec<FallenLeavesTreeDecorator> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return ModTreeDecoratorType.FALLEN_LEAVES_TREE_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        BlockPos logPosition = context.logs().getFirst();
        generateRandomLeavesBlock(context, logPosition);
    }

    private void generateRandomLeavesBlock(Context context, BlockPos logPosition) {
        Random random = new Random();
        int probability = 3;
        int from = 0;
        int to = 3;

        for (int i = from; i < to; i++) {
            for (int j = from; j < to; j++) {
                //TODO: I have no idea why I did this, please refactor
                generateFallenLeavesByProbability(random, probability, context, logPosition, to - i + from, j + 1);
                generateFallenLeavesByProbability(random, probability, context, logPosition, to - i + from, -j - 1);
                generateFallenLeavesByProbability(random, probability, context, logPosition, -to + i - from, j + 1);
                generateFallenLeavesByProbability(random, probability, context, logPosition, -to + i - from, -j - 1);

                generateFallenLeavesByProbability(random, probability, context, logPosition, i + 1, 0);
                generateFallenLeavesByProbability(random, probability, context, logPosition, -i - 1, 0);
                generateFallenLeavesByProbability(random, probability, context, logPosition, 0, i + 1);
                generateFallenLeavesByProbability(random, probability, context, logPosition, 0, i - 1);
            }
        }
    }

    private void generateFallenLeavesByProbability(Random random, int probability, Context context, BlockPos logPosition, int i, int j) {
        int randomNumber = random.nextInt(100 - 1 + 1) + 1;
        if (randomNumber < probability) {
            placeFallenLeavesBlockUpAndDown(context, logPosition.north(i).east(j).above());
        }
    }

    private void placeFallenLeavesBlockUpAndDown(Context context, BlockPos petalPosition) {
        for (int i = 0; i < 3; i++) {
            placeFallenLeavesBlock(context, petalPosition.above(i));
            placeFallenLeavesBlock(context, petalPosition.below(i));
        }
    }

    private void placeFallenLeavesBlock(Context context, BlockPos fallenLeavesPosition) {
        LevelSimulatedReader world = context.level();

        if ((world instanceof WorldGenLevel) && areLeavesBlockPlantable(context, fallenLeavesPosition)) {
            BlockPos leavesPosition = context.leaves().getFirst();
            Block leavesBlock = ((WorldGenLevel) world).getBlockState(leavesPosition).getBlock();

            if (!context.isAir(leavesPosition)) {
                context.setBlock(fallenLeavesPosition, leavesBlock.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
            }
        }
    }

    private boolean areLeavesBlockPlantable(Context context, BlockPos leavesPosition) {
        BlockPos groundPosition = leavesPosition.below();
        boolean isGroundSoil = Feature.isGrassOrDirt(context.level(), groundPosition);
        boolean isLeavesPositionAir = context.isAir(leavesPosition);
        boolean isAboveBlockPositionAir = context.isAir(leavesPosition.above());

        return isGroundSoil && isLeavesPositionAir && isAboveBlockPositionAir;
    }
}
