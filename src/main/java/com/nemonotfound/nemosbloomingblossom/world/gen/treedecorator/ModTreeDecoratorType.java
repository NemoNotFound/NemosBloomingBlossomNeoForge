package com.nemonotfound.nemosbloomingblossom.world.gen.treedecorator;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.mojang.text2speech.Narrator.LOGGER;
import static com.nemonotfound.nemosbloomingblossom.NemosBloomingBlossom.MOD_ID;

public class ModTreeDecoratorType {

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, MOD_ID);
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<CherryTreeDecorator>> CHERRY_TREE_DECORATOR =  TREE_DECORATOR_TYPES.register("cherry_tree_decorator", () -> new TreeDecoratorType<>(CherryTreeDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<FallenLeavesTreeDecorator>> FALLEN_LEAVES_TREE_DECORATOR = TREE_DECORATOR_TYPES.register("fallen_leaves_tree_decorator", () -> new TreeDecoratorType<>(FallenLeavesTreeDecorator.CODEC));

    public static void registerTreeDecoratorTypes(IEventBus modEventBus) {
        LOGGER.info("Registering tree decorator types");

        TREE_DECORATOR_TYPES.register(modEventBus);
    }
}
