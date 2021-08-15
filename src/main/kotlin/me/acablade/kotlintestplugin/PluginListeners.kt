package me.acablade.kotlintestplugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.*
import java.util.function.Consumer

class PluginListeners(val plugin: KotlinTestPlugin):Listener {

    @EventHandler
    fun onKill(event: PlayerDeathEvent){

        val deadPlayer: Player = event.entity

        val damageEvent: EntityDamageEvent? = deadPlayer.lastDamageCause

        when(damageEvent){
            is EntityDamageByEntityEvent -> {
                val damageByEntityEvent: EntityDamageByEntityEvent = damageEvent
                val damager: Player? = if(damageByEntityEvent.damager is Player) damageByEntityEvent.damager as Player else null
                val damagerGamePlayer: GamePlayer? = damager?.uniqueId?.let { GamePlayer.get(it) }
                val deadGamePlayer: GamePlayer = GamePlayer.get(deadPlayer.uniqueId)
                deadGamePlayer.deaths++
                damagerGamePlayer?.let { it.kills++ }
            }
            else -> return
        }

    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){

        val player: Player = event.player

        plugin.gameManager?.game?.let {
            player.teleport(it.lobby!!)
            it.playerList!!.add(GamePlayer.get(player.uniqueId))
            it.itemList.forEach {
                item -> kotlin.run {
                    player.inventory.clear()
                    player.inventory.addItem(item)
                    player.updateInventory()
                }
            }
        }

    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent){
        val player: Player = event.player

        plugin.gameManager?.game?.let {
            it.playerList.remove(GamePlayer.get(player.uniqueId))
        }
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent){

        val player: Player = event.player

        var location: Location? = null

        plugin.gameManager?.game?.locations?.let {
            location = it[Random().nextInt(it.size-1)]
        }


        Bukkit.getScheduler().runTaskLater(plugin, Consumer {
            location.let {
                if (it != null) {
                    player.teleport(it)
                }
            }
        },5)


    }


}