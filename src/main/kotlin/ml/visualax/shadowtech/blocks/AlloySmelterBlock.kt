package ml.visualax.shadowtech.blocks

import ml.visualax.shadowtech.ShadowTechMod
import ml.visualax.shadowtech.gui.STScreenHandlerFactory
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class AlloySmelterBlock(settings: Settings?) : Block(settings), BlockEntityProvider {
    override fun createBlockEntity(blockView: BlockView?): BlockEntity? {
        return AlloySmelterBlockEntity()
    }

    override fun onUse(
            state: BlockState?,
            world: World,
            pos: BlockPos?,
            player: PlayerEntity?,
            hand: Hand?,
            hit: BlockHitResult?
    ): ActionResult? {
        if (world.isClient) return ActionResult.SUCCESS

        player?.openHandledScreen(STScreenHandlerFactory(ShadowTechMod.ALLOY_SMELTER_HANDLER as ExtendedScreenHandlerType<*>, pos!!))
        return ActionResult.SUCCESS
    }
}
