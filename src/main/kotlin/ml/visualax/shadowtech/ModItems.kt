package ml.visualax.shadowtech

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

class ModItems {
    companion object {
        var STEEL_INGOT = Item(Item.Settings().group(ShadowTechMod.ITEM_GROUP))
        var COPPER_INGOT = Item(Item.Settings().group(ShadowTechMod.ITEM_GROUP))
        var TIN_INGOT = Item(Item.Settings().group(ShadowTechMod.ITEM_GROUP))
    }
}

