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
    // ITEMS
    val ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.build(
        Identifier("shadowtech", "general"),
        { ItemStack(STEEL_INGOT) })
    val STEEL_INGOT = Item(Item.Settings().group(ITEM_GROUP))
    val COPPER_INGOT = Item(Item.Settings().group(ITEM_GROUP))
    val TIN_INGOT = Item(Item.Settings().group(ITEM_GROUP))

    // BLOCKS
    val ALLOY_SMELTER = AlloySmelterBlock(
        FabricBlockSettings
            .of(Material.METAL)
            .breakByHand(false)
            .breakByTool(FabricToolTags.PICKAXES)
            .strength(10.5f, 2.2f)
            .sounds(BlockSoundGroup.ANVIL)
            .build()
    )
    val COAL_GENERATOR = CoalGeneratorBlock(
        FabricBlockSettings
            .of(Material.METAL)
            .breakByHand(false)
            .breakByTool(FabricToolTags.PICKAXES)
            .strength(10.5f, 2.2f)
            .sounds(BlockSoundGroup.ANVIL)
            .build()
    )
    val STEEL_BLOCK = Block(
        FabricBlockSettings
            .of(Material.METAL)
            .breakByHand(false)
            .breakByTool(FabricToolTags.PICKAXES)
            .strength(10.5f, 2.2f)
            .sounds(BlockSoundGroup.METAL)
            .build()
    )
    val MACHINE_FRAME = Block(
        FabricBlockSettings
            .of(Material.METAL)
            .breakByHand(false)
            .breakByTool(FabricToolTags.PICKAXES)
            .strength(5.5f, 1.2f)
            .sounds(BlockSoundGroup.ANVIL)
            .nonOpaque()
            .build()
    )

    companion object {
        var ALLOY_SMELTER_BLOCK_ENTITY: BlockEntityType<AlloySmelterBlockEntity>? = null
        var COAL_GENERATOR_BLOCK_ENTITY: BlockEntityType<CoalGeneratorBlockEntity>? = null
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
        Registry.register(Registry.ITEM, Identifier("shadowtech", "steel_ingot"), STEEL_INGOT)
        Registry.register(Registry.ITEM, Identifier("shadowtech", "copper_ingot"), COPPER_INGOT)
        Registry.register(Registry.ITEM, Identifier("shadowtech", "tin_ingot"), TIN_INGOT)
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "alloy_smelter"),
            BlockItem(ALLOY_SMELTER, Item.Settings().group(ITEM_GROUP))
        )
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "coal_generator"),
            BlockItem(COAL_GENERATOR, Item.Settings().group(ITEM_GROUP))
        )
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "steel_block"),
            BlockItem(STEEL_BLOCK, Item.Settings().group(ITEM_GROUP))
        )
        Registry.register(
            Registry.ITEM,
            Identifier("shadowtech", "machine_frame"),
            BlockItem(MACHINE_FRAME, Item.Settings().group(ITEM_GROUP))
        )

        // BLOCKS
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "machine_frame"), MACHINE_FRAME)
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "alloy_smelter"), ALLOY_SMELTER)
        ALLOY_SMELTER_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "shadowtech:alloy_smelter",
            BlockEntityType.Builder.create(Supplier { AlloySmelterBlockEntity() }, ALLOY_SMELTER).build(null)
        );
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "coal_generator"), COAL_GENERATOR)
        COAL_GENERATOR_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "shadowtech:coal_generator",
            BlockEntityType.Builder.create(Supplier { CoalGeneratorBlockEntity() }, COAL_GENERATOR).build(null)
        );
        Registry.register(Registry.BLOCK, Identifier("shadowtech", "steel_block"), STEEL_BLOCK)

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