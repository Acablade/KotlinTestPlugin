package me.acablade.kotlintestplugin

import java.util.*

class GamePlayer(val playerUUID: UUID) {


    companion object{
        val gamePlayerHashMap: HashMap<UUID, GamePlayer> = HashMap()
        fun get(uuid: UUID): GamePlayer {
            val gamePlayer:GamePlayer? = gamePlayerHashMap.get(uuid)
            when(gamePlayer){
                null -> {
                    val newGamePlayer = GamePlayer(uuid)
                    gamePlayerHashMap.put(uuid,newGamePlayer)
                    return newGamePlayer
                }
                else -> return gamePlayer
            }
        }
    }

    var kills:Int = 0
    var deaths:Int = 0

}