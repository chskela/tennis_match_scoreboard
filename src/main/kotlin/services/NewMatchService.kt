package services

import models.entities.CurrentMatch
import models.entities.Player
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class NewMatchService {
    fun createNewMatch(player1: Player, player2: Player): UUID {
        val uuid = UUID.randomUUID()
        currentMatch[uuid] = CurrentMatch(player1, player2)
        log.info("Create new Match with UUID $uuid and players $player1 and $player2")
        return uuid
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
        private val currentMatch: MutableMap<UUID, CurrentMatch> = mutableMapOf()
    }
}