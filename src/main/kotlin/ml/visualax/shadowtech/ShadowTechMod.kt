package ml.visualax.shadowtech

import ml.visualax.shadowtech.blocks.*
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.RenderLayer
import net.minecraft.item.*
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import java.util.function.Supplier

class ShadowTechMod {
    companion object {
        var ALLOY_SMELTER_BLOCK_ENTITY: BlockEntityType<AlloySmelterBlockEntity>? = null
        var ALLOY_SMELTER_HANDLER: ExtendedScreenHandlerType<AlloySmelterBlockController>? = null;

        var COAL_GENERATOR_BLOCK_ENTITY: BlockEntityType<CoalGeneratorBlockEntity>? = null
        var COAL_GENERATOR_HANDLER: ExtendedScreenHandlerType<CoalGeneratorBlockController>? = null;

        val MAIN_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.build(
                Identifier("shadowtech", "main")
        ) { ItemStack(ModBlocks.COAL_GENERATOR) }
        val MATERIALS_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.build(
                Identifier("shadowtech", "ores")
        ) { ItemStack(ModBlocks.COPPER_ORE) }
    }

    fun handleBiome(b: Biome) {
        if (b.category != Biome.Category.NETHER) {
            b.addFeature(
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.ORE.configure(
                            OreFeatureConfig(
                                    OreFeatureConfig.Target.NATURAL_STONE,
                                    ModBlocks.COPPER_ORE.defaultState,
                                    9
                            )
                    ).createDecoratedFeature(
                            Decorator.COUNT_RANGE.configure(RangeDecoratorConfig(
                                    9,
                                    0,
                                    1,
                                    84
                            ))
                    )
            )
        }

        if (b.category != Biome.Category.NETHER) {
            b.addFeature(
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.ORE.configure(
                            OreFeatureConfig(
                                    OreFeatureConfig.Target.NATURAL_STONE,
                                    ModBlocks.TIN_ORE.defaultState,
                                    6
                            )
                    ).createDecoratedFeature(
                            Decorator.COUNT_RANGE.configure(RangeDecoratorConfig(
                                    7,
                                    0,
                                    2,
                                    82
                            ))
                    )
            )
        }
    }

    @Suppress("unused")
    fun initClient() {
        // RENDER
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(),
                ModBlocks.LIGHT_MACHINE_FRAME
        )

        // GUIS
        ScreenRegistry.register(ALLOY_SMELTER_HANDLER) { controller, inventory, _ ->
            AlloySmelterBlockScreen(controller, inventory.player)
        }

        ScreenRegistry.register(COAL_GENERATOR_HANDLER) { controller, inventory, _ ->
            CoalGeneratorBlockScreen(controller, inventory.player)
        }
    }

    @Suppress("unused")
    fun initMain() {
        // ITEMS
        Registry.register(Registry.ITEM, Identifier("shadowtech", "steel_ingot"), ModItems.STEEL_INGOT)
        Registry.register(Registry.ITEM, Identifier("shadowtech", "copper_ingot"), ModItems.COPPER_INGOT)
        Registry.register(Registry.ITEM, Identifier("shadowtech", "tin_ingot"), ModItems.TIN_INGOT)
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "alloy_smelter"),
                BlockItem(ModBlocks.ALLOY_SMELTER, Item.Settings().group(MAIN_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "coal_generator"),
                BlockItem(ModBlocks.COAL_GENERATOR, Item.Settings().group(MAIN_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "steel_block"),
                BlockItem(ModBlocks.STEEL_BLOCK, Item.Settings().group(MATERIALS_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "copper_block"),
                BlockItem(ModBlocks.COPPER_BLOCK, Item.Settings().group(MATERIALS_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "tin_block"),
                BlockItem(ModBlocks.TIN_BLOCK, Item.Settings().group(MATERIALS_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "copper_ore"),
                BlockItem(ModBlocks.COPPER_ORE, Item.Settings().group(MATERIALS_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "tin_ore"),
                BlockItem(ModBlocks.TIN_ORE, Item.Settings().group(MATERIALS_ITEM_GROUP))
        )
        Registry.register(
                Registry.ITEM,
                Identifier("shadowtech", "light_machine_frame"),
                BlockItem(ModBlocks.LIGHT_MACHINE_FRAME, Item.Settings().group(MAIN_ITEM_GROUP))
        )

        // BLOCKS
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "light_machine_frame"), ModBlocks.LIGHT_MACHINE_FRAME)
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "alloy_smelter"), ModBlocks.ALLOY_SMELTER)
        ALLOY_SMELTER_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                "shadowtech:alloy_smelter",
                BlockEntityType.Builder.create(Supplier { AlloySmelterBlockEntity() }, ModBlocks.ALLOY_SMELTER).build(null)
        );
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "coal_generator"), ModBlocks.COAL_GENERATOR)
        COAL_GENERATOR_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                "shadowtech:coal_generator",
                BlockEntityType.Builder.create(Supplier { CoalGeneratorBlockEntity() }, ModBlocks.COAL_GENERATOR).build(null)
        );
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "steel_block"), ModBlocks.STEEL_BLOCK)
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "copper_block"), ModBlocks.COPPER_BLOCK)
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "tin_block"), ModBlocks.TIN_BLOCK)
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "copper_ore"), ModBlocks.COPPER_ORE)
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "tin_ore"), ModBlocks.TIN_ORE)

        // ORES
        Registry.BIOME.forEach(this::handleBiome)
        RegistryEntryAddedCallback.event(Registry.BIOME).register(RegistryEntryAddedCallback { _: Int, _: Identifier?, biome: Biome? -> handleBiome(biome!!) })

        // GUIS
        ALLOY_SMELTER_HANDLER = ScreenHandlerRegistry.registerExtended(Identifier("shadowtech", "alloy_smelter")) { syncId, playerInventory, buf ->
            AlloySmelterBlockController(
                    syncId,
                    playerInventory,
                    ScreenHandlerContext.create(playerInventory.player.world, buf.readBlockPos())
            )
        } as ExtendedScreenHandlerType<AlloySmelterBlockController>
        COAL_GENERATOR_HANDLER = ScreenHandlerRegistry.registerExtended(Identifier("shadowtech", "coal_generator")) { syncId, playerInventory, buf ->
            CoalGeneratorBlockController(
                    syncId,
                    playerInventory,
                    ScreenHandlerContext.create(playerInventory.player.world, buf.readBlockPos())
            )
        } as ExtendedScreenHandlerType<CoalGeneratorBlockController>
    }
}
