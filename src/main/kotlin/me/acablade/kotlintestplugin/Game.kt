package me.acablade.kotlintestplugin

import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.*

data class Game(val plugin: KotlinTestPlugin){

    val playerList: MutableList<GamePlayer> = ArrayList()

    var lobby: Location? = null

    val locations: MutableList<Location> = ArrayList()

    val itemList:MutableList<ItemStack> = ArrayList()

}
