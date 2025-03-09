package dev.thebjoredcraft.ultimatemoderation.inventory

import org.bukkit.inventory.ItemStack

interface InventoryAction {
    val name: String

    val slot: Int
    val item: ItemStack

    fun execute()
}