package ml.visualax.shadowtech.interfaces

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList


interface ImplementedInventory : Inventory {
    /**
     * Gets the item list of this inventory.
     * Must return the same instance every time it's called.
     */
    val items: DefaultedList<ItemStack?>

    /**
     * Returns the inventory size.
     */
    override fun size(): Int {
        return items.size
    }

    /**
     * @return true if this inventory has only empty stacks, false otherwise
     */
    override fun isEmpty(): Boolean {
        for (i in 0 until size()) {
            val stack = getStack(i)
            if (!stack.isEmpty) {
                return false
            }
        }
        return true
    }

    /**
     * Gets the item in the slot.
     */
    override fun getStack(slot: Int): ItemStack {
        return items[slot]
    }

    /**
     * Takes a stack of the size from the slot.
     *
     * (default implementation) If there are less items in the slot than what are requested,
     * takes all items in that slot.
     */
    override fun removeStack(slot: Int, count: Int): ItemStack? {
        val result = Inventories.splitStack(items, slot, count)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    /**
     * Removes the current stack in the `slot` and returns it.
     */
    override fun removeStack(slot: Int): ItemStack? {
        return Inventories.removeStack(items, slot)
    }

    override fun clear() {
        items.clear()
    }

    override fun markDirty() {
        // Override if you want behavior.
    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean {
        return true
    }

    /**
     * Replaces the current stack in the `slot` with the provided stack.
     *
     * If the stack is too big for this inventory ([Inventory.getMaxCountPerStack]),
     * it gets resized to this inventory's maximum amount.
     */
    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if (stack.count > maxCountPerStack) {
            stack.count = maxCountPerStack
        }
    }

    companion object {
        // Creation
        /**
         * Creates an inventory from the item list.
         */
        fun of(items: DefaultedList<ItemStack?>): ImplementedInventory = object :
                ImplementedInventory {
            override val items = items
        }

        /**
         * Creates a new inventory with the size.
         */
        fun ofSize(size: Int): ImplementedInventory? {
            return of(DefaultedList.ofSize(size, ItemStack.EMPTY))
        }
    }
}

