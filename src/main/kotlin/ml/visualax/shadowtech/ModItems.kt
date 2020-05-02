package ml.visualax.shadowtech

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

class ModItems {
    companion object {
        val STEEL_INGOT = Item(Item.Settings().group(ShadowTechMod.ITEM_GROUP))
        val COPPER_INGOT = Item(Item.Settings().group(ShadowTechMod.ITEM_GROUP))
        val TIN_INGOT = Item(Item.Settings().group(ShadowTechMod.ITEM_GROUP))
    }
}
