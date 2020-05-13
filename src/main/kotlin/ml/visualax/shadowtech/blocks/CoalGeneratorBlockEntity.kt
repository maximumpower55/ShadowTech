package ml.visualax.shadowtech.blocks

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import ml.visualax.shadowtech.ImplementedInventory
import ml.visualax.shadowtech.ShadowTechMod
import net.minecraft.block.entity.BlockEntity
import net.minecraft.container.PropertyDelegate
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.DefaultedList
import net.minecraft.util.Tickable
import team.reborn.energy.EnergySide
import team.reborn.energy.EnergyStorage
import team.reborn.energy.EnergyTier


class CoalGeneratorBlockEntity : BlockEntity(ShadowTechMod.COAL_GENERATOR_BLOCK_ENTITY), EnergyStorage, ImplementedInventory, PropertyDelegateHolder, Tickable {
    var storedEnergy = 0.0
    override val items: DefaultedList<ItemStack?> = DefaultedList.ofSize(1, ItemStack.EMPTY)

    override fun fromTag(tag: CompoundTag?) {
        super.fromTag(tag)
        Inventories.fromTag(tag, items)
        storedEnergy = tag?.getDouble("Energy")!!
    }

    override fun toTag(tag: CompoundTag?): CompoundTag? {
        Inventories.toTag(tag, items)
        tag?.putDouble("Energy", storedEnergy)
        return super.toTag(tag)
    }

    override fun isValidInvStack(slot: Int, stack: ItemStack?): Boolean {
        return isValidFuel(stack);
    }

    override fun markDirty() {
        super<ImplementedInventory>.markDirty()
    }

    override fun setStored(energy: Double) {
        storedEnergy = energy
    }

    override fun getMaxStoredPower(): Double {
        return 100.0
    }

    override fun getTier(): EnergyTier {
        return EnergyTier.MEDIUM
    }

    override fun getStored(p0: EnergySide?): Double {
        return storedEnergy
    }

    override fun tick() {
        generateEnergy()
    }

    fun isValidFuel(stack: ItemStack?): Boolean {
        return stack != null && (stack.item == Items.COAL || stack.item == Items.CHARCOAL)
    }

    var energyTick = 0
    fun generateEnergy() {
        val stack = getInvStack(0);
        if (isValidFuel(stack)) {
            if (propertyDelegate[0] < propertyDelegate[1]) {
                if (energyTick >= 20) {
                    stack.count--
                    storedEnergy += 2.5
                    energyTick = 0
                }

                energyTick++
            }
        }
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
