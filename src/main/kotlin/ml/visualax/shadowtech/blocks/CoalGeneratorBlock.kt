package ml.visualax.shadowtech.blocks

import ml.visualax.shadowtech.ShadowTechMod
import ml.visualax.shadowtech.gui.STScreenHandlerFactory
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.particle.ParticleTypes
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
import java.util.*


class CoalGeneratorBlock(settings: Settings?) : Block(settings), BlockEntityProvider {
    init {
        this.defaultState = stateManager.defaultState.with(FACING, Direction.NORTH).with(ACTIVE, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        super.appendProperties(builder)
        builder?.add(ACTIVE, FACING)
    }

    override fun getPlacementState(context: ItemPlacementContext?): BlockState? {
        for (facing in context!!.placementDirections) {
            val state = defaultState.with(FACING, facing.opposite)
            if (state.canPlaceAt(context!!.world, context!!.blockPos)) {
                return state
            }
        }

        return null;
    }

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
        if (world.isClient) return ActionResult.SUCCESS

        player?.openHandledScreen(STScreenHandlerFactory(ShadowTechMod.COAL_GENERATOR_HANDLER as ExtendedScreenHandlerType<*>, pos!!))
        return ActionResult.SUCCESS
    }

    @Environment(EnvType.CLIENT)
    override fun randomDisplayTick(state: BlockState?, world: World, pos: BlockPos, random: Random?) {
        if (state?.contains(ACTIVE) == true && state[ACTIVE]) {
            val d = pos.x.toDouble() + 0.5
            val e = pos.y.toDouble() + 1.0
            val f = pos.z.toDouble() + 0.5
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0)
        }
    }

    companion object {
        val ACTIVE: BooleanProperty = BooleanProperty.of("active")
        val FACING: DirectionProperty = Properties.FACING
    }
}
