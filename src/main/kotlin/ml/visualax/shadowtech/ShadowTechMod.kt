package ml.visualax.shadowtech

import ml.visualax.shadowtech.blocks.*
import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.BlockContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

class ShadowTechMod {
    companion object {
        var ALLOY_SMELTER_BLOCK_ENTITY: BlockEntityType<AlloySmelterBlockEntity>? = null
        var COAL_GENERATOR_BLOCK_ENTITY: BlockEntityType<CoalGeneratorBlockEntity>? = null
        val ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.build(
                Identifier("shadowtech", "general"),
                { ItemStack(ModItems.STEEL_INGOT) })
    }

    @Suppress("unused")
    fun initClient() {
        // GUIS
        ScreenProviderRegistry.INSTANCE.registerFactory(Identifier("shadowtech", "coal_generator")
        ) { syncId: Int, identifier: Identifier?, player: PlayerEntity, buf: PacketByteBuf ->
            CoalGeneratorBlockScreen(
                CoalGeneratorBlockController(
                    syncId,
                    player.inventory,
                    BlockContext.create(player.world, buf.readBlockPos())
                ),
                player
            )
        }

    }

    @Suppress("unused")
    fun initMain() {
        // ITEMS
        Registry.register(Registry.ITEM, Identifier("shadowtech", "steel_ingot"), ModItems.STEEL_INGOT)
        Registry.register(Registry.ITEM, Identifier("shadowtech", "copper_ingot"), ModItems.COPPER_INGOT)
        Registry.register(Registry.ITEM, Identifier("shadowtech", "tin_ingot"),  ModItems.TIN_INGOT)
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "alloy_smelter"),
            BlockItem(ModBlocks.ALLOY_SMELTER, Item.Settings().group(ITEM_GROUP))
        )
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "coal_generator"),
            BlockItem(ModBlocks.COAL_GENERATOR, Item.Settings().group(ITEM_GROUP))
        )
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "steel_block"),
            BlockItem(ModBlocks.STEEL_BLOCK, Item.Settings().group(ITEM_GROUP))
        )
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "machine_frame"),
            BlockItem(ModBlocks.MACHINE_FRAME, Item.Settings().group(ITEM_GROUP))
        )

        // BLOCKS
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "machine_frame"), ModBlocks.MACHINE_FRAME)
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

        // GUIS
        ContainerProviderRegistry.INSTANCE.registerFactory(
            Identifier("shadowtech", "coal_generator")
        ) { syncId: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf ->
            CoalGeneratorBlockController(
                syncId,
                player.inventory,
                BlockContext.create(player.world, buf.readBlockPos())
            )
        }
    }
}