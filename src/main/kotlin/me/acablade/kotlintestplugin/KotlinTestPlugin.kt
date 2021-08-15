package me.acablade.kotlintestplugin

import org.bukkit.*
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Consumer

class KotlinTestPlugin: JavaPlugin() {

    var gameManager: GameManager? = null

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(PluginListeners(this),this)
        gameManager = GameManager(this)
        gameManager?.game = Game(this)
        Bukkit.getScheduler().runTaskTimer(
            this,
            Consumer {
                GamePlayer.gamePlayerHashMap.forEach{
                    (_,gamePlayer) ->
                    run {
                        Bukkit.broadcastMessage("${gamePlayer.deaths} : ${gamePlayer.kills}")
                    }
                }
            },
            0,
            5*10)
        val world: World = Bukkit.getWorlds().get(0)

        world.setGameRule(GameRule.KEEP_INVENTORY,true)

        gameManager?.game!!.locations.add(Location(world, 10.0, 155.0, 0.0, 0F,0F))
        gameManager?.game!!.locations.add(Location(world, 10.0, 155.0, 10.0, 0F,0F))
        gameManager?.game!!.locations.add(Location(world, 0.0, 155.0, 10.0, 0F,0F))
        gameManager?.game!!.locations.add(Location(world, 0.0, 155.0, 0.0, 0F,0F))
        gameManager?.game!!.lobby = Location(world,20.0,155.0,0.0,0F,0F)

        gameManager?.game!!.itemList.add(ItemStack(Material.IRON_SWORD))
    }

}