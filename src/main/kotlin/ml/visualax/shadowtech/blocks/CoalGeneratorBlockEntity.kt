package ml.visualax.shadowtech.blocks

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import ml.visualax.shadowtech.ShadowTechMod
import ml.visualax.shadowtech.interfaces.ImplementedInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Tickable
import net.minecraft.util.collection.DefaultedList
import team.reborn.energy.EnergySide
import team.reborn.energy.EnergyStorage
import team.reborn.energy.EnergyTier


class CoalGeneratorBlockEntity : BlockEntity(ShadowTechMod.COAL_GENERATOR_BLOCK_ENTITY), EnergyStorage, ImplementedInventory, PropertyDelegateHolder, Tickable, NamedScreenHandlerFactory {
    var storedEnergy = 0.0
    override val items: DefaultedList<ItemStack?> = DefaultedList.ofSize(1, ItemStack.EMPTY)

    override fun createMenu(syncId: Int, inv: PlayerInventory?, player: PlayerEntity?): ScreenHandler? {
        println("creating")
        return CoalGeneratorBlockController(syncId, inv, ScreenHandlerContext.create(world, pos))
    }

    override fun getDisplayName(): Text {
        return TranslatableText("block.shadowtech.coal_generator")
    }

    override fun fromTag(state: BlockState?, tag: CompoundTag?) {
        Inventories.fromTag(tag, items)
        setStored(tag?.getDouble("Energy")!!)
        super.fromTag(state, tag)
    }

    override fun toTag(tag: CompoundTag?): CompoundTag? {
        Inventories.toTag(tag, items)
        tag?.putDouble("Energy", storedEnergy)
        return super.toTag(tag)
    }

    override fun isValid(slot: Int, stack: ItemStack?): Boolean {
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
    override fun getMaxOutput(side: EnergySide?): Double = 100.0

    override fun getMaxInput(side: EnergySide?): Double = 0.0

    override fun tick() {
        generateEnergy()
    }

    fun isValidFuel(stack: ItemStack?): Boolean {
        return stack != null && (stack.item == Items.COAL || stack.item == Items.CHARCOAL)
    }

    var energyTick = 0
    fun generateEnergy() {
        val stack = getStack(0);
        if (isValidFuel(stack)) {
            if (storedEnergy < maxStoredPower) {
                if (energyTick >= 6) {
                    storedEnergy++
                }

                if (energyTick >= 8) {
                    stack.count--
                    energyTick = 0
                }

                energyTick++
            }
        }

        val state = cachedState.with(CoalGeneratorBlock.ACTIVE, isValidFuel(stack) && storedEnergy < maxStoredPower)
        world!!.setBlockState(pos, state)
    }

    override fun getPropertyDelegate() = object : PropertyDelegate {
        override fun get(index: Int): Int = when (index) {
            0 -> storedEnergy.toInt()
            1 -> maxStoredPower.toInt()
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun set(index: Int, value: Int): Unit = when (index) {
            0 -> {
                storedEnergy = value.toDouble()
            }
            1 -> {
            } // Max values
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun size() = 2
    }
}
