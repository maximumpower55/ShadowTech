package ml.visualax.shadowtech.blocks

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import ml.visualax.shadowtech.ShadowTechMod
import ml.visualax.shadowtech.interfaces.ImplementedInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.PropertyDelegate
import net.minecraft.util.Tickable
import net.minecraft.util.collection.DefaultedList
import team.reborn.energy.EnergyHolder
import team.reborn.energy.EnergyTier

class AlloySmelterBlockEntity : BlockEntity(ShadowTechMod.ALLOY_SMELTER_BLOCK_ENTITY), EnergyHolder, ImplementedInventory, Tickable, PropertyDelegateHolder {
    var storedEnergy = 0.0
    override val items: DefaultedList<ItemStack?> = DefaultedList.ofSize(3, ItemStack.EMPTY)

    override fun fromTag(state: BlockState?, tag: CompoundTag?) {
        Inventories.fromTag(tag, items)
        storedEnergy = tag?.getDouble("Energy")!!
        super.fromTag(state, tag)
    }

    override fun toTag(tag: CompoundTag?): CompoundTag? {
        Inventories.toTag(tag, items)
        tag?.putDouble("Energy", storedEnergy)
        return super.toTag(tag)
    }

    override fun getTier(): EnergyTier {
        return EnergyTier.MEDIUM
    }

    override fun tick() {
    }

    override fun markDirty() {
        super<ImplementedInventory>.markDirty()
    }

    override fun getMaxStoredPower(): Double {
        return 99.0
    }

    override fun getPropertyDelegate() = object : PropertyDelegate {
        override fun get(index: Int): Int = when (index) {
            0 -> storedEnergy.toInt()
            1 -> maxStoredPower.toInt()
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun set(index: Int, value: Int): Unit = when (index) {
            0 -> { storedEnergy = value.toDouble() }
            1 -> {} // Max values
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun size() = 2
    }
}
