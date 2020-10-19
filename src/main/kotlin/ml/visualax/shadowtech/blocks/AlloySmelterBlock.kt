package ml.visualax.shadowtech.blocks

import ml.visualax.shadowtech.ShadowTechMod
import ml.visualax.shadowtech.gui.STScreenHandlerFactory
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

class AlloySmelterBlock(settings: Settings?) : Block(settings), BlockEntityProvider {
    init {
        this.defaultState = stateManager.defaultState.with(FACING, Direction.NORTH).with(ACTIVE, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        super.appendProperties(builder)
        builder?.add(CoalGeneratorBlock.ACTIVE, CoalGeneratorBlock.FACING)
    }

    override fun getPlacementState(context: ItemPlacementContext?): BlockState? {
        if (context != null) {
            return defaultState.with(CoalGeneratorBlock.FACING, context.playerFacing.opposite) as BlockState
        }

        return null
    }

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

    companion object {
        val ACTIVE: BooleanProperty = BooleanProperty.of("active")
        val FACING: DirectionProperty = Properties.FACING
    }
}
