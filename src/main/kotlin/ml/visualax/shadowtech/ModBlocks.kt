package ml.visualax.shadowtech

import ml.visualax.shadowtech.blocks.AlloySmelterBlock
import ml.visualax.shadowtech.blocks.CoalGeneratorBlock
import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup

class ModBlocks {
    companion object {
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
    }
}
