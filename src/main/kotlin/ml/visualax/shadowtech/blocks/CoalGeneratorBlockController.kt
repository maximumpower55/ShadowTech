package ml.visualax.shadowtech.blocks

import io.github.cottonmc.cotton.gui.CottonCraftingController
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.data.Alignment
import net.minecraft.container.BlockContext
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.recipe.RecipeType
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class CoalGeneratorBlockController(syncId: Int, playerInventory: PlayerInventory?, context: BlockContext?):
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

        val label = WLabel(TranslatableText("block.shadowtech.coal_generator"))
        label.setAlignment(Alignment.CENTER)
        root.add(label, 0, 0, 9, 1)

        val energybar = WBar(ENERGY_BAR_EMPTY, ENERGY_BAR_FULL, 0, 1, WBar.Direction.UP).withTooltip("gui.shadowtech.energy")
        root.add(energybar, 0, 0, 1, 3);

        val itemSlot = WItemSlot.of(blockInventory, 0)
        root.add(itemSlot, 4, 1)
        root.add(createPlayerInventoryPanel(), 0, 4)

        root.validate(this)
    }

    companion object {
        private val ENERGY_BAR_EMPTY = Identifier("shadowtech", "textures/gui/energy_bar_empty.png")
        private val ENERGY_BAR_FULL = Identifier("shadowtech", "textures/gui/energy_bar_full.png")
    }
}
