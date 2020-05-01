package ml.visualax.shadowtech.blocks

import ml.visualax.shadowtech.ShadowTechMod
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.Tickable
import team.reborn.energy.EnergyHolder
import team.reborn.energy.EnergyTier


class AlloySmelterBlockEntity : BlockEntity(ShadowTechMod.ALLOY_SMELTER_BLOCK_ENTITY), EnergyHolder, Tickable {
    override fun getMaxStoredPower(): Double {
        return 1000000000.0
    }

    override fun getTier(): EnergyTier {
        return EnergyTier.LOW
    }

    override fun tick() {

    }
}