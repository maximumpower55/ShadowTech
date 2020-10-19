package ml.visualax.shadowtech.blocks

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import ml.visualax.shadowtech.ShadowTechMod
import ml.visualax.shadowtech.gui.GuiReference
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.TranslatableText

class CoalGeneratorBlockController(syncId: Int, playerInventory: PlayerInventory?, context: ScreenHandlerContext?):
        SyncedGuiDescription(
                ShadowTechMod.COAL_GENERATOR_HANDLER,
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
        label.horizontalAlignment = HorizontalAlignment.CENTER
        root.add(label, 0, 0, 9, 1)

        val energybar = WBar(GuiReference.EnergyBarEmpty, GuiReference.EnergyBarFull, 0, 1, WBar.Direction.UP).withTooltip(TranslatableText("gui.shadowtech.energy", propertyDelegate[0], propertyDelegate[1]))
        root.add(energybar, 0, 0, 1, 3);

        val itemSlot = WItemSlot.of(blockInventory, 0)
        root.add(itemSlot, 4, 1)
        root.add(createPlayerInventoryPanel(), 0, 4)

        root.validate(this)
    }
}
