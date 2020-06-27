package ml.visualax.shadowtech.blocks

import io.github.cottonmc.cotton.gui.CottonCraftingController
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.data.Alignment
import ml.visualax.shadowtech.gui.GuiReferences
import net.minecraft.container.BlockContext
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.recipe.RecipeType
import net.minecraft.text.TranslatableText

class AlloySmelterBlockController(syncId: Int, playerInventory: PlayerInventory?, context: BlockContext?):
      CottonCraftingController(
                RecipeType.SMELTING,
                syncId,
                playerInventory,
                getBlockInventory(context),
                getBlockPropertyDelegate(context)
        ) {
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(150, 90)

        val label = WLabel(TranslatableText("block.shadowtech.alloy_smelter"))
        label.setAlignment(Alignment.CENTER)
        root.add(label, 0, 0, 9, 1)

        val energybar = WBar(GuiReferences.ENERGY_BAR_EMPTY, GuiReferences.ENERGY_BAR_FULL, 0, 1, WBar.Direction.UP).withTooltip("gui.shadowtech.energy")
        root.add(energybar, 0, 0, 1, 3)

        val itemSlot0 = WItemSlot.of(blockInventory, 0)
        root.add(itemSlot0, 3, 1)

        val itemSlot1 = WItemSlot.of(blockInventory, 1)
        root.add(itemSlot1, 4, 1)

        val itemSlot2 = WItemSlot.of(blockInventory, 2)
        itemSlot2.isModifiable = false
        root.add(itemSlot2, 6, 1)
        root.add(createPlayerInventoryPanel(), 0, 4)

        root.validate(this)
    }
}