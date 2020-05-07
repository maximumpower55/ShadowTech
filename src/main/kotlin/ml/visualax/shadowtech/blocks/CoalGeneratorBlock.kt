package ml.visualax.shadowtech.blocks

import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World


class CoalGeneratorBlock(settings: Settings?) : Block(settings), BlockEntityProvider {
    override fun createBlockEntity(blockView: BlockView?): BlockEntity? {
        return CoalGeneratorBlockEntity()
    }

    override fun onUse(
            state: BlockState?,
            world: World,
            pos: BlockPos?,
            player: PlayerEntity?,
            hand: Hand?,
            hit: BlockHitResult?
    ): ActionResult? {
        if (world.isClient) return ActionResult.PASS
        val be = world.getBlockEntity(pos)
        if (be != null && be is CoalGeneratorBlockEntity) {
            ContainerProviderRegistry.INSTANCE.openContainer(
                    Identifier("shadowtech", "coal_generator"),
                    player
            ) { packetByteBuf -> packetByteBuf.writeBlockPos(pos) }
        }
        return ActionResult.SUCCESS
    }
}
