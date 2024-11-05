package com.nemonotfound.nemosbloomingblossom.world.gen.treedecorator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FLOWER_AMOUNT;

public class CherryTreeDecorator extends TreeDecorator {

    public static final CherryTreeDecorator INSTANCE = new CherryTreeDecorator();
    public static final MapCodec<CherryTreeDecorator> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return ModTreeDecoratorType.CHERRY_TREE_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        BlockPos logPosition = context.logs().getFirst();
        generateRandomFLowers(context, logPosition, 80, 0, 2);
        generateRandomFLowers(context, logPosition, 50, 2, 4);
        generateRandomFLowers(context, logPosition, 40, 4, 5);
    }

    private void generateRandomFLowers(Context genercontexttor, BlockPos logPosition, int probability, int from, int to) {
        Random random = new Random();

        for (int i = from; i < to; i++) {
            for (int j = from; j < to; j++) {
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, to - i + from, j + 1);
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, to - i + from, -j - 1);
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, -to + i - from, j + 1);
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, -to + i - from, -j - 1);

                generateFlowersByProbability(random, probability, genercontexttor, logPosition, i + 1, 0);
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, -i - 1, 0);
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, 0, i + 1);
                generateFlowersByProbability(random, probability, genercontexttor, logPosition, 0, i - 1);
            }
        }
    }

    private void generateFlowersByProbability(Random random, int probability, Context context, BlockPos logPosition, int i, int j) {
        int randomNumber = random.nextInt(100 - 1 + 1) + 1;
        if (randomNumber < probability) {
            putFlowersUpAndDown(context, logPosition.north(i).east(j).above(), random);
        }
    }

    private void putFlowersUpAndDown(Context context, BlockPos petalPosition, Random random) {
        for (int i = 0; i < 3; i++) {
            putFlowers(context, petalPosition.above(i), random);
            putFlowers(context, petalPosition.below(i), random);
        }
    }

    private void putFlowers(Context context, BlockPos petalPosition, Random random) {
        if (isPetalPlantable(context, petalPosition)) {
            int randomNumber = random.nextInt(100 - 1 + 1) + 1;
            int petalCount = 1;

            if (randomNumber < 10) {
                petalCount = 4;
            } else if (10 < randomNumber && randomNumber < 20) {
                petalCount = 3;
            } else if (20 < randomNumber && randomNumber < 50) {
                petalCount = 2;
            }

            context.setBlock(petalPosition, Blocks.PINK_PETALS.defaultBlockState().setValue(FLOWER_AMOUNT, petalCount));
        }
    }

    private boolean isPetalPlantable(Context context, BlockPos petalPosition) {
        BlockPos petalGroundPosition = petalPosition.below();
        boolean isGroundSoil = Feature.isGrassOrDirt(context.level(), petalGroundPosition);
        boolean isPetalPositionAir = context.isAir(petalPosition);

        return isGroundSoil && isPetalPositionAir;
    }
}
