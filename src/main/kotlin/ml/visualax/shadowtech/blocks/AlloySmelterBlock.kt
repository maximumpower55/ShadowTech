package ml.visualax.shadowtech.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.entity.BlockEntity
import net.minecraft.world.BlockView

class AlloySmelterBlock(settings: Settings?) : Block(settings), BlockEntityProvider {
    override fun createBlockEntity(blockView: BlockView?): BlockEntity? {
        return AlloySmelterBlockEntity()
    }
}